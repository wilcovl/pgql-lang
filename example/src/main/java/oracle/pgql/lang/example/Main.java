/**
 * Copyright (C) 2013 - 2017 Oracle and/or its affiliates. All rights reserved.
 */
package oracle.pgql.lang.example;

import oracle.pgql.lang.PgqlException;
import oracle.pgql.lang.Pgql;
import oracle.pgql.lang.PgqlResult;

public class Main {

  public static void main(String[] args) throws PgqlException {

    Pgql pgql = new Pgql();

    // parse query and print graph query
    PgqlResult result1 = pgql.parse("PATH abc := () -/:xyz/-> ()\n" + "SELECT *\n"
        + "WHERE () -/:abc+/-> ()");
    System.out.println(result1.getGraphQuery());

    // parse query with errors and print error messages
//    PgqlResult result2 = pgql.parse("SELECT x, y, WHERE (n) -[e]-> (m)");
//    System.out.println(result2.getErrorMessages());
  }
}
