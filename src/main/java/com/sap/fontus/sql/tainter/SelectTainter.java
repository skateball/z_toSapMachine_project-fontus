package com.sap.fontus.sql.tainter;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.List;

import static com.sap.fontus.Constants.TAINT_PREFIX;

public class SelectTainter extends SelectVisitorAdapter {

	protected final List<Taint> taints;
	protected final List<Expression> expressionReference;
	protected final List<SelectItem> selectItemReference;
	protected List<AssignmentValue> assignmentValues;

	SelectTainter(List<Taint> taints) {
		this.taints = taints;
		// List used as Container to return the reference to one newly created
		// Expression by SelectExpressionTainter -> comparable to return object
		this.expressionReference = new ArrayList<>();
		// List used as Container to return the reference to one newly created
		// SelectItem by SelectItemTainter -> comparable to return object
		this.selectItemReference = new ArrayList<>();
	}

	public List<AssignmentValue> getAssignmentValues() {
		return this.assignmentValues;
	}

	public void setAssignmentValues(List<AssignmentValue> assignmentValues) {
		this.assignmentValues = assignmentValues;
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		if (plainSelect.getSelectItems() != null) {
			List<SelectItem> newSelectItems = new ArrayList<>();
			SelectItemTainter selectItemTainter = new SelectItemTainter(this.taints, this.selectItemReference);
			selectItemTainter.setAssignmentValues(this.assignmentValues);
			for (SelectItem selectItem : plainSelect.getSelectItems()) {
				newSelectItems.add(selectItem);

				// Check if nested query exists
				if (selectItem.toString().toLowerCase().contains("(select")) {
					// Safe and transform current alias
					Alias alias = ((SelectExpressionItem) selectItem).getAlias();
					Alias newAlias = new Alias("`" + TAINT_PREFIX + alias.getName().replace("\"", "").replace("`", "") + "`");

					List<Expression> plannedExpressions = new ArrayList<>();
					List<Table> tables = new ArrayList<>();
					List<Expression> where = new ArrayList<>();
					NestedSelectItemTainter nestedSelectItemTainter = new NestedSelectItemTainter(this.taints, this.selectItemReference, plannedExpressions, tables, where);
					selectItem.accept(nestedSelectItemTainter);


					// Expressions --> columns or values for functions like SUM, AVG, COUNT
					String expression = "";
					for (Expression e : plannedExpressions) {
						expression += e.toString() + ",";
					}
					expression = expression.substring(0, expression.length() - 1);

					// Table of nested query
					String strTable = "";
					if (tables.size() > 1) {
						System.err.println("Something went wrong, more than one table in nested query!");
					} else {
						strTable = tables.get(0).getName();
					}

					String nestedQuery = "";
					if (where.size() > 0) {
						nestedQuery = "SELECT " + expression + " FROM " + strTable + " WHERE " + where.get(0).toString();
					} else {
						nestedQuery = "SELECT " + expression + " FROM " + strTable;
					}

					// Yeah let's do SQL injection :D

					try {
						SubSelect sub = new SubSelect();
						sub.setSelectBody(((Select) CCJSqlParserUtil.parse(nestedQuery)).getSelectBody());
						sub.setAlias(newAlias);
						SelectExpressionItem ie = new SelectExpressionItem();
						ie.setExpression(sub);
						newSelectItems.add(ie);
					} catch (Exception ex) {
						System.err.println(ex);
					}
				} else {
					selectItem.accept(selectItemTainter);
					if (!this.selectItemReference.isEmpty()) {
						// get new created expression by reference in list and clear
						// list
						newSelectItems.add(this.selectItemReference.get(0));
						this.selectItemReference.clear();
					}
				}
			}
			plainSelect.setSelectItems(newSelectItems);
		}

		GroupByElement groupBy = plainSelect.getGroupBy();
		if(groupBy != null) {
			List<Expression> groupByExpressions = groupBy.getGroupByExpressions();
			List<Expression> taintedGroupByExpressions = this.taintGroupBy(groupByExpressions);
			groupBy.setGroupByExpressions(taintedGroupByExpressions);
		}
	}

	protected List<Expression> taintGroupBy(List<Expression> groupByColumnReferences) {
		if (groupByColumnReferences != null) {
			List<Expression> newGroupByColumnReferences;
			newGroupByColumnReferences = new ArrayList<>();
			ExpressionTainter selectExpressionTainter = new ExpressionTainter(this.taints, this.expressionReference);
			selectExpressionTainter.setAssignmentValues(this.assignmentValues);
			for (Expression expression : groupByColumnReferences) {
				newGroupByColumnReferences.add(expression);
				expression.accept(selectExpressionTainter);
				if (!this.expressionReference.isEmpty()) {
					// get new created expression by reference in list and clear
					// list
					newGroupByColumnReferences.add(this.expressionReference.get(0));
					this.expressionReference.clear();
				}
			}
			return newGroupByColumnReferences;
		}
		return null;
	}

	@Override
	public void visit(SetOperationList setOperationsList) {
		// offset, fetch, limit, order by not relevant
		if (setOperationsList.getSelects() != null)
			for (SelectBody selectBody : setOperationsList.getSelects()) {
				selectBody.accept(this);
			}
	}

	@Override
	public void visit(WithItem withItem) {
		SelectTainter selectTainter = new SelectTainter(this.taints);
		selectTainter.setAssignmentValues(this.assignmentValues);
		withItem.getSubSelect().getSelectBody().accept(selectTainter);
		if (withItem.getWithItemList() != null) {
			List<SelectItem> newWithItemList = new ArrayList<>();
			SelectItemTainter selectItemTainter = new SelectItemTainter(this.taints, this.selectItemReference);
			selectItemTainter.setAssignmentValues(this.assignmentValues);
			for (SelectItem selectItem : withItem.getWithItemList()) {
				newWithItemList.add(selectItem);
				selectItem.accept(selectItemTainter);
				if(!this.selectItemReference.isEmpty()){
					newWithItemList.add(this.selectItemReference.get(0));
					this.selectItemReference.clear();
				}
			}
			withItem.setWithItemList(newWithItemList);
		}
	}
}
