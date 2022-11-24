package com.sampledomain.bank.helper;

import jdk.jfr.Description;

/**
 * Types of passwords to login for a card entity. Pin is related to card entity and fingerprint is related to user's fingerprint.
 */
public enum PasswordType {
    //@Description("PIN")
    Pin,

    //@Description("fingerprint")
    Fingerprint
}
