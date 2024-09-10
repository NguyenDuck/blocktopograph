package io.vn.nguyenduck.blocktopograph.shell;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringJoiner;

import moe.shizuku.server.IRemoteProcess;
import moe.shizuku.server.IShizukuService;
import rikka.shizuku.Shizuku;

public class Runner {

    private static boolean checkedRoot;
    private static boolean hasRooted;

    public static boolean isRooted() {
        if (!checkedRoot) {
            try {
                Process process = Runtime.getRuntime().exec("su");
                process.getOutputStream().close();
                hasRooted = process.exitValue() == 0;
            } catch (Exception ignored) {
            }
            checkedRoot = true;
        }

        return hasRooted;
    }

    private static class IRunCommandResult {
        BufferedReader output;
        BufferedReader error;

        IRunCommandResult(BufferedReader output, BufferedReader error) {
            this.output = output;
            this.error = error;
        }
    }

    private static IRunCommandResult runCommand(String command) throws Exception {
        if (isRooted()) {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "root", "sh", "-c", command});
            return new IRunCommandResult(
                    new BufferedReader(new InputStreamReader(process.getInputStream())),
                    new BufferedReader(new InputStreamReader(process.getErrorStream()))
            );
        } else {
            IShizukuService shizukuService = IShizukuService.Stub.asInterface(Shizuku.getBinder());
            if (shizukuService == null) {
                throw new Exception("Shizuku service is null");
            }
            IRemoteProcess process = shizukuService.newProcess(new String[]{"sh", "-c", command}, null, null);

            return new IRunCommandResult(
                    new BufferedReader(new InputStreamReader(new FileInputStream(process.getInputStream().getFileDescriptor()))),
                    new BufferedReader(new InputStreamReader(new FileInputStream(process.getErrorStream().getFileDescriptor())))
            );
        }
    }

    private static String run(CharSequence delimiter, String command) throws Exception {
        IRunCommandResult result = runCommand(command);

        BufferedReader output = result.output;
        BufferedReader error = result.error;

        StringJoiner o = new StringJoiner(delimiter);
        for (var r = output.readLine(); r != null; r = output.readLine()) o.add(r);

        StringJoiner e = new StringJoiner(delimiter);
        for (var r = error.readLine(); r != null; r = error.readLine()) e.add(r);

        if (e.length() > 0) throw new Exception(e.toString());
        return o.toString();
    }

    private static String run(String command) throws Exception {
        return run("", command);
    }

    public static byte[] run(String... commandSplit) throws Exception {
        return run(String.join(" ", commandSplit)).getBytes();
    }

    public static String runString(String command) throws Exception {
        return run("\n", command);
    }

    public static String runString(String... commandSplit) throws Exception {
        return runString(String.join(" ", commandSplit));
    }
}