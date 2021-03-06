import org.biojava.bio.alignment.AlignmentPair;
import sinclair.basic.ZX81SysVars;

import java.util.Arrays;

public class FroggingProgramAlign {
    public static void main(String[] args) throws Exception {
        byte[] reconstructionFileBytes = FroggingProgram.loadReconstructionFileBytes();

        byte[] originalFileBytes = FroggingProgram.loadFileBytes();
        originalFileBytes = BitUtils.ins(originalFileBytes, 8 * (ZX81SysVars.PRBUFF - ZX81SysVars.SAVE_START), false);
        originalFileBytes = BitUtils.ins(originalFileBytes, 8 * (ZX81SysVars.MEMBOT - ZX81SysVars.SAVE_START), false);

        // first line
        AlignmentPair alignmentPair = AlignmentUtils.align(originalFileBytes, 16509 - ZX81SysVars.SAVE_START, 16, reconstructionFileBytes, 16509 - ZX81SysVars.SAVE_START, 16);

        System.out.println(alignmentPair.formatOutput(1000));

        System.out.println(AlignmentUtils.zx81CodesForGappedSequence(alignmentPair.getQuery()));
        System.out.println(alignmentPair.getQuery().seqString());
        System.out.println(alignmentPair.getSubject().seqString());
        System.out.println(AlignmentUtils.zx81CodesForGappedSequence(alignmentPair.getSubject()));

        Line l1 = new Line(1, new int[] {234, 11, 43, 55, 52, 44, 44, 46, 51, 44, 11, 118});
        BitUtils.printLine(reconstructionFileBytes, 8 * (16509 - ZX81SysVars.SAVE_START), 12, l1);

        // whole program

        int len = 16969 - 16509; // ignore last line since its length is unclear
        alignmentPair = AlignmentUtils.align(originalFileBytes, 16509 - ZX81SysVars.SAVE_START, len, reconstructionFileBytes, 16509 - ZX81SysVars.SAVE_START, len);

        // 3% mismatch - this is the error rate
        System.out.println(alignmentPair.formatOutput(150));

    }
}
