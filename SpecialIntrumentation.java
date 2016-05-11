import BIT.highBIT.*;
import java.io.File;
import java.math.BigInteger;
import java.util.Enumeration;


public class SpecialIntrumentation {


    private static BigInteger dyn_method_count = new BigInteger("0");
    private static BigInteger dyn_bb_count = new BigInteger("0");
    private static BigInteger loadcount = new BigInteger("0");
    private static BigInteger storecount = new BigInteger("0");
    private static BigInteger fieldloadcount = new BigInteger("0");
    private static BigInteger fieldstorecount = new BigInteger("0");
    private static BigInteger dyn_instr_count = new BigInteger("0");



    public static void doDynamic(File in_dir, File out_dir)
    {
        String filelist[] = in_dir.list();

        for (int i = 0; i < filelist.length; i++) {
            String filename = filelist[i];
            if (filename.endsWith(".class")) {
                String in_filename = in_dir.getAbsolutePath() + System.getProperty("file.separator") + filename;
                String out_filename = out_dir.getAbsolutePath() + System.getProperty("file.separator") + filename;
                ClassInfo ci = new ClassInfo(in_filename);
                for (Enumeration e = ci.getRoutines().elements(); e.hasMoreElements(); ) {
                    Routine routine = (Routine) e.nextElement();

                    for (Enumeration b = routine.getBasicBlocks().elements(); b.hasMoreElements(); ) {
                        BasicBlock bb = (BasicBlock) b.nextElement();
                        bb.addBefore("SpecialIntrumentation", "dynInstrCount", new Integer(bb.size()));
                    }

                    for (Enumeration instrs = (routine.getInstructionArray()).elements(); instrs.hasMoreElements(); ) {
                        Instruction instr = (Instruction) instrs.nextElement();
                        int opcode=instr.getOpcode();
                        if (opcode == InstructionTable.getfield)
                            instr.addBefore("SpecialIntrumentation", "LSFieldCount", new Integer(0));
                        else if (opcode == InstructionTable.putfield)
                            instr.addBefore("SpecialIntrumentation", "LSFieldCount", new Integer(1));
                        else {
                            short instr_type = InstructionTable.InstructionTypeTable[opcode];
                            if (instr_type == InstructionTable.LOAD_INSTRUCTION) {
                                instr.addBefore("SpecialIntrumentation", "LSCount", new Integer(0));
                            }
                            else if (instr_type == InstructionTable.STORE_INSTRUCTION) {
                                instr.addBefore("SpecialIntrumentation", "LSCount", new Integer(1));
                            }
                        }
                    }
                }
                ci.addAfter("SpecialIntrumentation", "printDynamic", "null");
                ci.write(out_filename);
            }
        }
    }

    public static synchronized void printDynamic(String foo)
    {
        System.out.println("Dynamic information summary:");
        System.out.println("Number of basic blocks: " + dyn_bb_count);
        System.out.println("Number of instructions: " + dyn_instr_count);
        System.out.println("Field load:    " + fieldloadcount);
        System.out.println("Field store:   " + fieldstorecount);
        System.out.println("Regular load:  " + loadcount);
        System.out.println("Regular store: " + storecount);
    }


    public static synchronized void dynInstrCount(int incr)
    {
        dyn_instr_count = dyn_instr_count.add(BigInteger.valueOf(incr));
        dyn_bb_count=dyn_bb_count.add(BigInteger.ONE);
    }

    public static synchronized void LSFieldCount(int type)
    {
        if (type == 0)
            fieldloadcount=fieldloadcount.add(BigInteger.ONE);
        else
            fieldstorecount = fieldstorecount.add(BigInteger.ONE);
    }

    public static synchronized void LSCount(int type)
    {
        if (type == 0)
            loadcount=loadcount.add(BigInteger.ONE);
        else
            storecount= storecount.add(BigInteger.ONE);
    }

    public static void printUsage()
    {
        System.out.println("you need to pass to path: one with the directory of the classes to instrument a the another path for the output files");
        System.exit(-1);
    }
    public static void main (String argv[])
    {
        if (argv.length != 2) {
            printUsage();
        }

        try {
            File in_dir = new File(argv[0]);
            File out_dir = new File(argv[1]);

            if (in_dir.isDirectory() && out_dir.isDirectory()) {
               // doLoadStore(in_dir, out_dir);
                doDynamic(in_dir,out_dir);
            }
            else {
                printUsage();
            }
        }
        catch (NullPointerException e) {
            printUsage();
        }

    }

}
