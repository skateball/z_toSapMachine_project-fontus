package com.sap.fontus.gdpr.metadata;

public abstract class GdprMetadataBase implements GdprMetadata {

    @Override
    public String toString() {
        return "SimpleGdprMetadata{" +
                "allowedPurposes=" + getAllowedPurposes() +
                ", protectionLevel=" + getProtectionLevel() +
                ", dataSubject=" + getSubject() +
                ", dataId=" + getId() +
                ", portability=" + isQualifiedForPortability() +
                ", consent=" + isConsentGiven() +
                ", identifiability=" + isIdentifiabible() +
                '}';
    }
}
