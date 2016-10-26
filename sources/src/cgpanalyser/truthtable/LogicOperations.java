/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.truthtable;

/*
- Comment line start with "-"
- Number: 0 to 15, linked to function numbering in evolution record file
- Name: choose from the table below, NOT case-sensitive
- Symbol: use whatever symbol desired (will be shown in gate)
- Color: hexa representation of color of gates with the function
-
- operation	 name	alternative name
- F_0        F		0
- F_1        AND	.
- F_2        A.~B	.~
- F_3        A		A
- F_4        ~A.B	~A.
- F_5        B		B
- F_6        XOR	^
- F_7        OR	+
- F_8        NOR	~+
- F_9        XNOR	~^
- F_(10)     ~B	~B
- F_(11)     A+~B	+~
- F_(12)     ~A	~A
- F_(13)     ~A+B	~A+
- F_(14)     NAND	~.
- F_(15)     T		1
-Number Name Symbol Color
0 and & ff0000
1 or + 00ff00
2 xor ^ 0000ff
3 nand ~& 0000ff
 */
/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public abstract class LogicOperations {

	public static String getExpression(String name, String a, String b) {

		switch (name) {
			case "f":
			case "0":
				return LogicOperations.getF0False(a, b);
			case "and":
			case ".":
				return LogicOperations.getF1And(a, b);
			case "a.~b":
			case ".~":
				return LogicOperations.getF2AAndNotB(a, b);
			case "a":
				return LogicOperations.getF3A(a, b);
			case "~a.b":
			case "~a.":
				return LogicOperations.getF4NotAAndB(a, b);
			case "b":
				return LogicOperations.getF5B(a, b);
			case "xor":
			case "^":
				return LogicOperations.getF6Xor(a, b);
			case "or":
			case "+":
				return LogicOperations.getF7Or(a, b);
			case "nor":
			case "~+":
				return LogicOperations.getF8Nor(a, b);
			case "xnor":
			case "~^":
				return LogicOperations.getF9Xnor(a, b);
			case "~b":
				return LogicOperations.getF10NotB(a, b);
			case "a+~b":
			case "+~":
				return LogicOperations.getF11AOrNotB(a, b);
			case "~a":
				return LogicOperations.getF12NotA(a, b);
			case "~a+b":
			case "~a+":
				return LogicOperations.getF13NotAOrB(a, b);
			case "nand":
			case "~.":
				return LogicOperations.getF14Nand(a, b);
			case "t":
			case "1":
				return LogicOperations.getF15True(a, b);
		}

		return null;
	}

	private static String getF0False(String a, String b) { //((a^a)+(b^b))
		return "((" + a + "^" + a + ")+(" + b + "^" + b + "))";
	}

	private static String getF1And(String a, String b) { //(a+b)
		return "(" + a + "." + b + ")";
	}

	private static String getF2AAndNotB(String a, String b) { //(a.~b)
		return "(" + a + ".~" + b + ")";
	}

	private static String getF3A(String a, String b) { //(a+(b^b))
		return "(" + a + "+(" + b + "^" + b + "))";
	}

	private static String getF4NotAAndB(String a, String b) { //(~a.b)
		return "(~" + a + "." + b + ")";
	}

	private static String getF5B(String a, String b) { //(b+(a^a))
		return "(" + b + "+(" + a + "^" + a + "))";
	}

	private static String getF6Xor(String a, String b) { //(a^b)
		return "(" + a + "^" + b + ")";
	}

	private static String getF7Or(String a, String b) { //(a+b)
		return "(" + a + "+" + b + ")";
	}

	private static String getF8Nor(String a, String b) { //(a~+b)
		return "(" + a + "~+" + b + ")";
	}

	private static String getF9Xnor(String a, String b) { //(a~^b)
		return "(" + a + "~^" + b + ")";
	}

	private static String getF10NotB(String a, String b) { //(~(b+(a^a)))
		return "(~(" + b + "+(" + a + "^" + a + ")))";
	}

	private static String getF11AOrNotB(String a, String b) { //(a+~b)
		return "(" + a + "+~" + b + ")";
	}

	private static String getF12NotA(String a, String b) { //(~(a+(b^b)))
		return "(~(" + a + "+(" + b + "^" + b + ")))";
	}

	private static String getF13NotAOrB(String a, String b) { //(~a+b)
		return "(~" + a + "+" + b + ")";
	}

	private static String getF14Nand(String a, String b) { //(a~.b)
		return "(" + a + "~." + b + ")";
	}

	private static String getF15True(String a, String b) { //(~(a^a)+(b^b))
		return "(~(" + a + "^" + a + ")+(" + b + "^" + b + "))";
	}
}
