/**
 * Copyright (C) 2013 - 2017 Oracle and/or its affiliates. All rights reserved.
 */
package oracle.pgql.lang.ir;

import oracle.pgql.lang.ir.QueryExpression.Aggregation.AggrAvg;
import oracle.pgql.lang.ir.QueryExpression.Aggregation.AggrCount;
import oracle.pgql.lang.ir.QueryExpression.Aggregation.AggrMax;
import oracle.pgql.lang.ir.QueryExpression.Aggregation.AggrMin;
import oracle.pgql.lang.ir.QueryExpression.Aggregation.AggrSum;
import oracle.pgql.lang.ir.QueryExpression.ArithmeticExpression.Add;
import oracle.pgql.lang.ir.QueryExpression.ArithmeticExpression.Div;
import oracle.pgql.lang.ir.QueryExpression.ArithmeticExpression.Mod;
import oracle.pgql.lang.ir.QueryExpression.ArithmeticExpression.Mul;
import oracle.pgql.lang.ir.QueryExpression.ArithmeticExpression.Sub;
import oracle.pgql.lang.ir.QueryExpression.ArithmeticExpression.UMin;
import oracle.pgql.lang.ir.QueryExpression.BindVariable;
import oracle.pgql.lang.ir.QueryExpressionVisitor;
import oracle.pgql.lang.ir.QueryExpression.ConstNull;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstBoolean;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstDate;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstDecimal;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstInteger;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstString;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstTime;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstTimeWithTimezone;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstTimestamp;
import oracle.pgql.lang.ir.QueryExpression.Constant.ConstTimestampWithTimezone;
import oracle.pgql.lang.ir.QueryExpression.Function.AllDifferent;
import oracle.pgql.lang.ir.QueryExpression.Function.GetLongitude;
import oracle.pgql.lang.ir.QueryExpression.Function.Cast;
import oracle.pgql.lang.ir.QueryExpression.Function.EdgeLabel;
import oracle.pgql.lang.ir.QueryExpression.Function.HasLabel;
import oracle.pgql.lang.ir.QueryExpression.Function.HasProp;
import oracle.pgql.lang.ir.QueryExpression.Function.Id;
import oracle.pgql.lang.ir.QueryExpression.Function.InDegree;
import oracle.pgql.lang.ir.QueryExpression.Function.OutDegree;
import oracle.pgql.lang.ir.QueryExpression.Function.Regex;
import oracle.pgql.lang.ir.QueryExpression.Function.VertexLabels;
import oracle.pgql.lang.ir.QueryExpression.LogicalExpression.And;
import oracle.pgql.lang.ir.QueryExpression.LogicalExpression.Not;
import oracle.pgql.lang.ir.QueryExpression.LogicalExpression.Or;
import oracle.pgql.lang.ir.QueryExpression.PropertyAccess;
import oracle.pgql.lang.ir.QueryExpression.RelationalExpression.Equal;
import oracle.pgql.lang.ir.QueryExpression.RelationalExpression.Greater;
import oracle.pgql.lang.ir.QueryExpression.RelationalExpression.GreaterEqual;
import oracle.pgql.lang.ir.QueryExpression.RelationalExpression.Less;
import oracle.pgql.lang.ir.QueryExpression.RelationalExpression.LessEqual;
import oracle.pgql.lang.ir.QueryExpression.RelationalExpression.NotEqual;
import oracle.pgql.lang.ir.QueryExpression.Star;
import oracle.pgql.lang.ir.QueryExpression.VarRef;

public interface QueryExpressionVisitor {

  public void visit(ConstInteger constInteger);

  public void visit(ConstDecimal constDecimal);

  public void visit(ConstString constString);

  public void visit(ConstBoolean constBoolean);

  public void visit(ConstDate constDate);

  public void visit(ConstTime constTime);

  public void visit(ConstTimestamp constTimestamp);

  public void visit(ConstTimeWithTimezone constTimeWithTimezone);

  public void visit(ConstTimestampWithTimezone constTimestampWithTimezone);

  public void visit(ConstNull constantNull);

  public void visit(Sub sub);

  public void visit(Add add);

  public void visit(Mul mul);

  public void visit(Div div);

  public void visit(Mod mod);

  public void visit(UMin uMin);

  public void visit(And and);

  public void visit(Or or);

  public void visit(Not not);

  public void visit(Equal equal);

  public void visit(NotEqual notEqual);

  public void visit(Greater greater);

  public void visit(GreaterEqual greaterEqual);

  public void visit(Less less);

  public void visit(LessEqual lessEqual);

  public void visit(AggrCount aggrCount);

  public void visit(AggrMin aggrMin);

  public void visit(AggrMax aggrMax);

  public void visit(AggrSum aggrSum);

  public void visit(AggrAvg aggrAvg);

  public void visit(VarRef varRef);

  public void visit(BindVariable bindVariable);

  public void visit(Star star);

  public void visit(Regex regex);

  public void visit(Id id);

  public void visit(PropertyAccess propAccess);

  public void visit(HasProp hasProp);

  public void visit(HasLabel hasLabel);

  public void visit(VertexLabels vertexLabels);

  public void visit(InDegree inDegree);

  public void visit(OutDegree outDegree);

  public void visit(EdgeLabel edgeLabel);

  public void visit(Cast cast);

  public void visit(AllDifferent allDifferent);

  public void visit(GetLongitude getLongitude);
}
