/**
 * Copyright (C) 2013 - 2017 Oracle and/or its affiliates. All rights reserved.
 */
package oracle.pgql.lang.ir;

import static oracle.pgql.lang.ir.PgqlUtils.printPgqlString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.stream.Collectors;

public interface QueryExpression {

  enum ExpressionType {

    // constants
    INTEGER,
    DECIMAL,
    STRING,
    BOOLEAN,
    DATE,
    TIME,
    TIMESTAMP,
    TIME_WITH_TIMEZONE,
    TIMESTAMP_WITH_TIMEZONE,
    NULL,

    // arithmetic expressions
    SUB,
    ADD,
    MUL,
    DIV,
    MOD,
    UMIN,

    // logical expressions
    AND,
    OR,
    NOT,

    // relational expressions
    EQUAL,
    NOT_EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,

    // aggregates
    AGGR_COUNT,
    AGGR_MIN,
    AGGR_MAX,
    AGGR_SUM,
    AGGR_AVG,

    // other
    VARREF,
    BIND_VARIABLE,
    STAR,

    // built-in functions
    REGEX,
    ID,
    PROP_ACCESS,
    HAS_PROP, // note: HasProp will be removed in the future
    HAS_LABEL, // note: HasLabel will be removed in the future
    VERTEX_LABELS,
    INDEGREE,
    OUTDEGREE, // vertex
    EDGE_LABEL,
    CAST,
    ALL_DIFFERENT,
    GET_LATITUDE,
    GET_LONGITUDE

  }

  ExpressionType getExpType();

  void accept(QueryExpressionVisitor v);

  abstract class UnaryExpression implements QueryExpression {

    private final QueryExpression exp;

    public UnaryExpression(QueryExpression exp) {
      this.exp = exp;
    }

    public QueryExpression getExp() {
      return exp;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      UnaryExpression that = (UnaryExpression) o;

      return exp.equals(that.exp);
    }

    @Override
    public int hashCode() {
      return exp.hashCode();
    }
  }

  abstract class BinaryExpression implements QueryExpression {

    private final QueryExpression exp1;
    private final QueryExpression exp2;

    public BinaryExpression(QueryExpression exp1, QueryExpression exp2) {
      this.exp1 = exp1;
      this.exp2 = exp2;
    }

    public QueryExpression getExp1() {
      return exp1;
    }

    public QueryExpression getExp2() {
      return exp2;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      BinaryExpression that = (BinaryExpression) o;

      if (!exp1.equals(that.exp1)) {
        return false;
      }
      return exp2.equals(that.exp2);
    }

    @Override
    public int hashCode() {
      int result = exp1.hashCode();
      result = 31 * result + exp2.hashCode();
      return result;
    }
  }

  // not yet used, but there will be built-in functions in the future that will need it
  abstract class TernaryExpression implements QueryExpression {

    private final QueryExpression exp1;
    private final QueryExpression exp2;
    private final QueryExpression exp3;

    public TernaryExpression(QueryExpression exp1, QueryExpression exp2, QueryExpression exp3) {
      this.exp1 = exp1;
      this.exp2 = exp2;
      this.exp3 = exp3;
    }

    public QueryExpression getExp1() {
      return exp1;
    }

    public QueryExpression getExp2() {
      return exp2;
    }

    public QueryExpression getExp3() {
      return exp3;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      TernaryExpression that = (TernaryExpression) o;

      if (!exp1.equals(that.exp1)) {
        return false;
      }
      if (!exp2.equals(that.exp2)) {
        return false;
      }
      return exp3.equals(that.exp3);
    }

    @Override
    public int hashCode() {
      int result = exp1.hashCode();
      result = 31 * result + exp2.hashCode();
      result = 31 * result + exp3.hashCode();
      return result;
    }
  }

  interface ArithmeticExpression extends QueryExpression {

    class Sub extends BinaryExpression implements ArithmeticExpression {
      public Sub(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.SUB;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " - " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Add extends BinaryExpression implements ArithmeticExpression {
      public Add(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.ADD;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " + " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Mul extends BinaryExpression implements ArithmeticExpression {
      public Mul(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.MUL;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " * " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Div extends BinaryExpression implements ArithmeticExpression {
      public Div(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.DIV;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " / " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Mod extends BinaryExpression implements ArithmeticExpression {
      public Mod(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.MOD;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " % " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class UMin extends UnaryExpression implements ArithmeticExpression {
      public UMin(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.UMIN;
      }

      @Override
      public String toString() {
        return "-(" + getExp() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }
  }

  interface LogicalExpression extends QueryExpression {

    class And extends BinaryExpression implements LogicalExpression {
      public And(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.AND;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " AND " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Or extends BinaryExpression implements LogicalExpression {
      public Or(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.OR;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " OR " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Not extends UnaryExpression implements LogicalExpression {
      public Not(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.NOT;
      }

      @Override
      public String toString() {
        return "!(" + getExp() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }
  }

  interface RelationalExpression extends QueryExpression {

    class Equal extends BinaryExpression implements RelationalExpression {
      public Equal(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.EQUAL;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " = " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class NotEqual extends BinaryExpression implements RelationalExpression {
      public NotEqual(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.NOT_EQUAL;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " != " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Greater extends BinaryExpression implements RelationalExpression {
      public Greater(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.GREATER;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " > " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class GreaterEqual extends BinaryExpression implements RelationalExpression {
      public GreaterEqual(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.GREATER_EQUAL;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " >= " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Less extends BinaryExpression implements RelationalExpression {
      public Less(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.LESS;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " < " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class LessEqual extends BinaryExpression implements RelationalExpression {
      public LessEqual(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.LESS_EQUAL;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " <= " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }
  }

  abstract class Constant<T> implements QueryExpression {

    protected final T value;

    public Constant(T value) {
      this.value = value;
    }

    public T getValue() {
      return value;
    }

    @Override
    public String toString() {
      return value.toString();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Constant<?> constant = (Constant<?>) o;

      return value.equals(constant.value);
    }

    @Override
    public int hashCode() {
      return value.hashCode();
    }

    public static class ConstInteger extends Constant<Long> {
      public ConstInteger(long val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.INTEGER;
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    public static class ConstDecimal extends Constant<Double> {
      public ConstDecimal(double val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.DECIMAL;
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    public static class ConstString extends Constant<String> {
      public ConstString(String val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.STRING;
      }

      @Override
      public String toString() {
        return printPgqlString(value);
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    public static class ConstBoolean extends Constant<Boolean> {

      public ConstBoolean(boolean val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.BOOLEAN;
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    public static class ConstDate extends Constant<LocalDate> {

      public ConstDate(LocalDate val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.DATE;
      }

      @Override
      public String toString() {
        return "DATE '" + value + "'";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    public static class ConstTime extends Constant<LocalTime> {

      public ConstTime(LocalTime val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.TIME;
      }

      @Override
      public String toString() {
        return "TIME '" + value + "'";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    public static class ConstTimestamp extends Constant<LocalDateTime> {

      public ConstTimestamp(LocalDateTime val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.TIMESTAMP;
      }

      @Override
      public String toString() {
        return "TIMESTAMP '" + value + "'";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    public static class ConstTimeWithTimezone extends Constant<OffsetTime> {

      public ConstTimeWithTimezone(OffsetTime val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.TIME_WITH_TIMEZONE;
      }

      @Override
      public String toString() {
        return "TIME '" + value + "'";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    public static class ConstTimestampWithTimezone extends Constant<OffsetDateTime> {

      public ConstTimestampWithTimezone(OffsetDateTime val) {
        super(val);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.TIMESTAMP_WITH_TIMEZONE;
      }

      @Override
      public String toString() {
        return "TIMESTAMP '" + value + "'";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }
  }

  class ConstNull implements QueryExpression {

    @Override
    public ExpressionType getExpType() {
      return ExpressionType.NULL;
    }

    @Override
    public String toString() {
      return "NULL";
    }

    @Override
    public void accept(QueryExpressionVisitor v) {
      v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 31;
    }
  }

  class VarRef implements QueryExpression {
    private final QueryVariable variable;

    public VarRef(QueryVariable variable) {
      this.variable = variable;
    }

    public QueryVariable getVariable() {
      return variable;
    }

    @Override
    public ExpressionType getExpType() {
      return ExpressionType.VARREF;
    }

    @Override
    public String toString() {
      return printPgqlString(variable);
    }

    @Override
    public void accept(QueryExpressionVisitor v) {
      v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      VarRef varRef = (VarRef) o;

      return variable.equals(varRef.variable);
    }

    @Override
    public int hashCode() {
      return variable.hashCode();
    }
  }

  class BindVariable implements QueryExpression {
    private final int parameterIndex;

    public BindVariable(int parameterIndex) {
      this.parameterIndex = parameterIndex;
    }

    public int getParameterIndex() {
      return parameterIndex;
    }

    @Override
    public ExpressionType getExpType() {
      return ExpressionType.BIND_VARIABLE;
    }

    @Override
    public String toString() {
      return "?";
    }

    @Override
    public void accept(QueryExpressionVisitor v) {
      v.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      BindVariable other = (BindVariable) obj;
      if (parameterIndex != other.parameterIndex)
        return false;
      return true;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + parameterIndex;
      return result;
    }
  }

  class PropertyAccess implements QueryExpression {
    private final QueryVariable variable;
    private final String propertyName;

    public PropertyAccess(QueryVariable variable, String propertyName) {
      this.variable = variable;
      this.propertyName = propertyName;
    }

    public QueryVariable getVariable() {
      return variable;
    }

    public String getPropertyName() {
      return propertyName;
    }

    @Override
    public ExpressionType getExpType() {
      return ExpressionType.PROP_ACCESS;
    }

    @Override
    public String toString() {
      return printPgqlString(variable) + "." + propertyName;
    }

    @Override
    public void accept(QueryExpressionVisitor v) {
      v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      PropertyAccess that = (PropertyAccess) o;

      if (!variable.equals(that.variable)) {
        return false;
      }
      return propertyName.equals(that.propertyName);
    }

    @Override
    public int hashCode() {
      int result = variable.hashCode();
      result = 31 * result + propertyName.hashCode();
      return result;
    }
  }

  interface Function extends QueryExpression {

    class Regex extends BinaryExpression implements Function {
      public Regex(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.REGEX;
      }

      @Override
      public String toString() {
        return "(" + getExp1() + " =~ " + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Id extends UnaryExpression implements Function {

      /**
       * Identifier of a node/edge
       *
       * @param exp
       *          an expression of type node or edge
       */
      public Id(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.ID;
      }

      @Override
      public String toString() {
        return getExp() + ".id()";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class EdgeLabel extends UnaryExpression implements Function {

      public EdgeLabel(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.EDGE_LABEL;
      }

      @Override
      public String toString() {
        return getExp() + ".label()";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class VertexLabels extends UnaryExpression implements Function {

      public VertexLabels(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.VERTEX_LABELS;
      }

      @Override
      public String toString() {
        return getExp() + ".labels()";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class HasLabel extends BinaryExpression implements Function {

      public HasLabel(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.HAS_LABEL;
      }

      @Override
      public String toString() {
        return getExp1() + ".hasLabel(" + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class HasProp extends BinaryExpression implements Function {

      /**
       * Whether a node/edge has a property.
       *
       * @param exp1
       *          an expression of type node or edge
       * @param exp2
       *          an expression of type String
       */
      public HasProp(QueryExpression exp1, QueryExpression exp2) {
        super(exp1, exp2);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.HAS_PROP;
      }

      @Override
      public String toString() {
        return getExp1() + ".has(" + getExp2() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class InDegree extends UnaryExpression implements Function {
      public InDegree(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.INDEGREE;
      }

      @Override
      public String toString() {
        return getExp() + ".indegree()";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class OutDegree extends UnaryExpression implements Function {
      public OutDegree(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.OUTDEGREE;
      }

      @Override
      public String toString() {
        return getExp() + ".outdegree()";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class Cast implements Function {

      private final QueryExpression exp;
      private final String targetTypeName;

      public Cast(QueryExpression exp, String targetTypeName) {
        this.exp = exp;
        this.targetTypeName = targetTypeName;
      }

      public QueryExpression getExp() {
        return exp;
      }

      public String getTargetTypeName() {
        return targetTypeName;
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.CAST;
      }

      @Override
      public String toString() {
        return "CAST(" + exp + " AS " + targetTypeName + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class AllDifferent implements Function {

      private final List<QueryExpression> exps;

      public AllDifferent(List<QueryExpression> exps) {
        this.exps = exps;
      }

      public List<QueryExpression> getExps() {
        return exps;
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.ALL_DIFFERENT;
      }

      @Override
      public String toString() {
        String expressions = exps.stream().map(QueryExpression::toString).collect(Collectors.joining(", "));
        return "ALL_DIFFERENT(" + expressions + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }


    class GetLatitude implements Function {

      private final QueryExpression exp;

      public GetLatitude(QueryExpression exps) {
        this.exp = exps;
      }

      public QueryExpression getExp() {
        return exp;
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.GET_LATITUDE;
      }

      @Override
      public String toString() {
        return "GET_LATITUDE(" + exp + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class GetLongitude implements Function {

      private final QueryExpression exp;

      public GetLongitude(QueryExpression exps) {
        this.exp = exps;
      }

      public QueryExpression getExp() {
        return exp;
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.GET_LONGITUDE;
      }

      @Override
      public String toString() {
        return "GET_LONGITUDE(" + exp + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }
  }

  interface Aggregation extends QueryExpression {

    class AggrCount extends UnaryExpression implements Aggregation {

      public AggrCount(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.AGGR_COUNT;
      }

      @Override
      public String toString() {
        return "COUNT(" + getExp() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class AggrMin extends UnaryExpression implements Aggregation {

      public AggrMin(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.AGGR_MIN;
      }

      @Override
      public String toString() {
        return "MIN(" + getExp() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class AggrMax extends UnaryExpression implements Aggregation {

      public AggrMax(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.AGGR_MAX;
      }

      @Override
      public String toString() {
        return "MAX(" + getExp() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class AggrSum extends UnaryExpression implements Aggregation {

      public AggrSum(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.AGGR_SUM;
      }

      @Override
      public String toString() {
        return "SUM(" + getExp() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }

    class AggrAvg extends UnaryExpression implements Aggregation {

      public AggrAvg(QueryExpression exp) {
        super(exp);
      }

      @Override
      public ExpressionType getExpType() {
        return ExpressionType.AGGR_AVG;
      }

      @Override
      public String toString() {
        return "AVG(" + getExp() + ")";
      }

      @Override
      public void accept(QueryExpressionVisitor v) {
        v.visit(this);
      }
    }
  }

  class Star implements QueryExpression {

    @Override
    public ExpressionType getExpType() {
      return ExpressionType.STAR;
    }

    @Override
    public String toString() {
      return "*";
    }

    @Override
    public void accept(QueryExpressionVisitor v) {
      v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 31;
    }
  }
}
