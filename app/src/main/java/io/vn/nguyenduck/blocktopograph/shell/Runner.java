package io.vn.nguyenduck.blocktopograph.shell;

import static io.vn.nguyenduck.blocktopograph.Constants.BOGGER;

import android.os.ParcelFileDescriptor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringJoiner;
import java.util.logging.Level;

import moe.shizuku.server.IRemoteProcess;
import moe.shizuku.server.IShizukuService;
import rikka.shizuku.Shizuku;

public class Runner {

    private static boolean checkedRoot = false;
    private static boolean hasRooted = false;

    public static boolean isRooted() {
        if (!checkedRoot) {
            try {
                Process process = Runtime.getRuntime().exec("su --help");
                process.getOutputStream().close();
                hasRooted = process.getErrorStream().available() == 0 || process.exitValue() == 0;
            } catch (Exception ignored) {
            }
            checkedRoot = true;
        }

        return hasRooted;
    }

    private static class IRunCommandResult {
        BufferedReader output;
        BufferedReader error;

        IRunCommandResult(Object output, Object error) {
            this.output = getBuffer(output);
            this.error = getBuffer(error);
        }

        private BufferedReader getBuffer(Object value) {
            if (value == null) return null;
            InputStream out = null;
            if (value instanceof ParcelFileDescriptor v)
                out = new FileInputStream(v.getFileDescriptor());
            else if (value instanceof InputStream v) out = v;
            return new BufferedReader(new InputStreamReader(out));
        }
    }

    private static IRunCommandResult runCommand(String command) throws Exception {
        if (isRooted()) {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "root", "sh", "-c", command});
            process.getOutputStream().close();
            return new IRunCommandResult(process.getInputStream(), process.getErrorStream());
        } else {
            IShizukuService shizukuService = IShizukuService.Stub.asInterface(Shizuku.getBinder());
            if (shizukuService == null) {
                throw new RuntimeException("Shizuku service is null");
            }
            IRemoteProcess process = shizukuService.newProcess(new String[]{"sh", "-c", command}, null, null);

            process.getOutputStream().close();
            return new IRunCommandResult(process.getInputStream(), process.getErrorStream());
        }
    }

    private static String run(CharSequence delimiter, String command) throws Exception {
        IRunCommandResult result = runCommand(command);

        BufferedReader output = result.output;
        BufferedReader error = result.error;

        StringJoiner o = new StringJoiner(delimiter);
        try {
            for (var r = output.readLine(); r != null; r = output.readLine()) o.add(r);
        } catch (IOException ignored) {
        }

        StringJoiner e = new StringJoiner(delimiter);
        try {
            for (var r = error.readLine(); r != null; r = error.readLine()) e.add(r);
        } catch (IOException ignored) {
        }

        if (e.length() > 0) throw new Exception(e.toString());
        return o.toString();
    }

    private static String run(String command) {
        try {
            return run("", command);
        } catch (Exception e) {
            BOGGER.log(Level.SEVERE, "", e);
            return "";
        }
    }

    public static byte[] run(String... commandSplit) {
        return run(String.join(" ", commandSplit)).getBytes();
    }

    private static String runString(String command) {
        try {
            return run("\n", command);
        } catch (Exception e) {
            BOGGER.log(Level.SEVERE, "", e);
            return "";
        }
    }

    public static String runString(String... commandSplit) {
        return runString(String.join(" ", commandSplit));
    }
}