package com.sap.fontus.sanitizer;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.Lexer;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.parser.Token;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SqlParsingTest {

    @Test
    void checkMultipleQueryParsing(){
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser("select * from table1; drop table table1", DbType.mysql);
        List<SQLStatement> statementList = parser.parseStatementList();
        assertFalse(statementList.size() != 2, "sql string should parse as 2 statements");
    }

    @Test
    void checkTokenParsing(){
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser("select column1 from table1 where column2='data'", DbType.mysql);
        List<SQLStatement> statementList = parser.parseStatementList();
        assertFalse(statementList.size() != 1, "sql string is a single statement");
        Lexer lexer = SQLParserUtils.createLexer("select column1 from table where column2='data'", DbType.mysql);
        lexer.nextToken();
        int count = 0;
        while(lexer.token() != Token.EOF){
            count++;
            lexer.nextToken();
        }
        assertFalse(count != 8, "incorrect no. of tokens while parsing");
    }
}
