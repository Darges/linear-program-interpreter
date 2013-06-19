import static java.lang.Math.max;

class interp {
	
  static void interp(Stm s) { 
	  /* you write this part */ 
	  Table t1 = null;
	  interpStm(s,t1); 
  }  
  
  private static Table interpStm(Stm stm, Table t)
  {
	  if (stm instanceof CompoundStm) {
          CompoundStm cstm = (CompoundStm) stm;
          return interpStm(cstm.stm2 ,interpStm(cstm.stm1, t));
      } else if (stm instanceof AssignStm) {
          AssignStm astm = (AssignStm) stm;
          return interpAssignStm(astm , t);
      } else { // Then it can be only PrintStm
          PrintStm pstm = (PrintStm) stm;
          return interpPrintStm(pstm , t);
      }   
  }

  private static Table interpAssignStm(AssignStm stm, Table t)
  {
        IntAndTable it = interpExp(stm.exp, t);
        return Table.update(it.t, stm.id, it.i);
  }
  
  
  private static Table interpPrintStm(PrintStm stm, Table t)
  {
	  ExpList eexps = stm.exps;	  
	  return interExpList(eexps, t);
  }
  
  private static Table interExpList(ExpList exps, Table t) 
  {
	  if (exps instanceof PairExpList) {
          PairExpList pexps = (PairExpList) exps;
          return interPairExpList(pexps, t);
      } else { // Then it can be LastExpList
          LastExpList lexps = (LastExpList) exps;
          return interLastExpList(lexps, t);
      }
  }
	
	private static Table interLastExpList(LastExpList lexps, Table t) {
		// TODO Auto-generated method stub
		IntAndTable it = interpExp(lexps.head, t);
		System.out.print(it.i+" ");
		return it.t;
	}
	
	private static Table interPairExpList(PairExpList pexps, Table t) {
		// TODO Auto-generated method stub
		IntAndTable it = interpExp(pexps.head, t);
		System.out.print(it.i+" ");	
		return interExpList(pexps.tail, it.t);
	}
	
	private static IntAndTable interpExp(Exp exp, Table t) {
		// TODO Auto-generated method stub
		if (exp instanceof IdExp)
	        return new IntAndTable(Table.lookup(t, ((IdExp) exp).id), t);
	    else if (exp instanceof NumExp)
	        return new IntAndTable(((NumExp) exp).num, t);
	    else if (exp instanceof OpExp) {
	        OpExp oexp = (OpExp) exp;
	        return interpOpExp(oexp, t);
	    } else { // Then it can be EseqExp
	        EseqExp eexp = (EseqExp) exp;
	        return interEseqExp(eexp, t);
	    }
	}
	/*
	 * ∂∫∫≈±Ì¥Ô Ω
	 */
	private static IntAndTable interEseqExp(EseqExp eexp, Table t) {
		// TODO Auto-generated method stub
		Table t1 = interpStm(eexp.stm, t);
		return interpExp(eexp.exp, t1);
	}


	private static IntAndTable interpOpExp(OpExp oexp, Table t) {
		// TODO Auto-generated method stub
		IntAndTable t2 = interpExp(oexp.left, t);
		IntAndTable t3 = interpExp(oexp.right, t2.t);
		switch(oexp.oper)
		{
		  case OpExp.Plus:
			  return new IntAndTable(t2.i + t3.i, t3.t);
		  case OpExp.Minus:
			  return new IntAndTable(t2.i - t3.i, t3.t);
		  case OpExp.Times:
			  return new IntAndTable(t2.i * t3.i, t3.t); 
		  case OpExp.Div:
			  return new IntAndTable(t2.i / t3.i, t3.t);	
		  default: 
			  return null;
		}		
	}


  
  static int maxargs(Stm s) { /* you write this part */                            
	  return _maxargs(s);
  }

  private static int _maxargs(Stm stm) {
      if (stm instanceof CompoundStm) {
          CompoundStm cstm = (CompoundStm) stm;
          return max(_maxargs(cstm.stm1), _maxargs(cstm.stm2));
      } else if (stm instanceof AssignStm) {
          AssignStm astm = (AssignStm) stm;
          return _maxargs(astm.exp);
      } else { // Then it can be only PrintStm
          PrintStm pstm = (PrintStm) stm;
          return max(countargs(pstm.exps), _maxargs(pstm.exps));
      }
  }
  
  private static int _maxargs(ExpList exps) {
      if (exps instanceof PairExpList) {
          PairExpList pexps = (PairExpList) exps;
          return max(_maxargs(pexps.head), _maxargs(pexps.tail));
      } else { // Then it can be LastExpList
          LastExpList lexps = (LastExpList) exps;
          return _maxargs(lexps.head);
      }
  }
  
  private static int _maxargs(Exp exp) {
      if (exp instanceof IdExp)
          return 0;
      else if (exp instanceof NumExp)
          return 0;
      else if (exp instanceof OpExp) {
          OpExp oexp = (OpExp) exp;
          return max(_maxargs(oexp.left), _maxargs(oexp.right));
      } else { // Then it can be EseqExp
          EseqExp eexp = (EseqExp) exp;
          return max(_maxargs(eexp.stm), _maxargs(eexp.exp));
      }
  }
  
  private static int countargs(ExpList exps) {
      if (exps instanceof LastExpList)
          return 1;
      else { // Then it is a PairExpList
          PairExpList pexps = (PairExpList) exps;
          return 1 + countargs(pexps.tail);
      }
  }
  
  public static void main(String args[]) throws java.io.IOException {
     System.out.println(maxargs(prog.prog));
     interp(prog.prog);
  }
}