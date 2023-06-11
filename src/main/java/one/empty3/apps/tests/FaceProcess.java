package one.empty3.apps.tests;

import one.empty3.feature.LocalExtremaProcess;
import one.empty3.feature.histograms.Hist4Contour2;
import one.empty3.io.ProcessFile;

import java.io.File;

public class FaceProcess extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        Hist4Contour2 hist4Contour2 = new Hist4Contour2();
        File file = new File(out.getParentFile() + "/" + "histo4contours2.jpg");
        File local = new File(out.getParentFile() + "/" + "localextremaprocess.jpg");
        LocalExtremaProcess localExtremaProcess = new LocalExtremaProcess();
        hist4Contour2.process(in, file);
        localExtremaProcess.process(file, out);


        return true;
    }
}
