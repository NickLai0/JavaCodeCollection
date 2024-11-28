package code.java.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import static code.java.utils.IOUtils.skipFully;

//不少API来源commons io哭的FileUtils和IOUtils
public class FileUtils {

    public static boolean makeDirIfDoesNotExist(String dir) {
        return makeDirIfDoesNotExist(new File(dir));
    }

    public static boolean makeDirIfDoesNotExist(File dir) {
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    //将整个文件转字符串
    public static String fileToString(File file) throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) file.length());
        try {
            fis = new FileInputStream(file);
            IOUtils.copy(fis, baos);
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return baos.toString();
    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    public static void copyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        } else if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        } else {
            File parentFile = destFile.getParentFile();
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            } else if (destFile.exists() && !destFile.canWrite()) {
                throw new IOException("Destination '" + destFile + "' exists but is read-only");
            } else {
                doCopyFile(srcFile, destFile, preserveFileDate);
            }
        }
    }

    public static long copyFile(File input, OutputStream output) throws IOException {
        FileInputStream fis = new FileInputStream(input);
        Throwable var3 = null;

        long var4;
        try {
            var4 = copyLarge(fis, output);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (fis != null) {
                if (var3 != null) {
                    try {
                        fis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    fis.close();
                }
            }

        }

        return var4;
    }

    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        } else {
            FileInputStream fis = new FileInputStream(srcFile);
            Throwable var4 = null;

            try {
                FileChannel input = fis.getChannel();
                Throwable var6 = null;

                try {
                    FileOutputStream fos = new FileOutputStream(destFile);
                    Throwable var8 = null;

                    try {
                        FileChannel output = fos.getChannel();
                        Throwable var10 = null;

                        try {
                            long size = input.size();
                            long pos = 0L;

                            long bytesCopied;
                            for (long count = 0L; pos < size; pos += bytesCopied) {
                                long remain = size - pos;
                                count = remain > 31457280L ? 31457280L : remain;
                                bytesCopied = output.transferFrom(input, pos, count);
                                if (bytesCopied == 0L) {
                                    break;
                                }
                            }
                        } catch (Throwable var91) {
                            var10 = var91;
                            throw var91;
                        } finally {
                            if (output != null) {
                                if (var10 != null) {
                                    try {
                                        output.close();
                                    } catch (Throwable var90) {
                                        var10.addSuppressed(var90);
                                    }
                                } else {
                                    output.close();
                                }
                            }

                        }
                    } catch (Throwable var93) {
                        var8 = var93;
                        throw var93;
                    } finally {
                        if (fos != null) {
                            if (var8 != null) {
                                try {
                                    fos.close();
                                } catch (Throwable var89) {
                                    var8.addSuppressed(var89);
                                }
                            } else {
                                fos.close();
                            }
                        }

                    }
                } catch (Throwable var95) {
                    var6 = var95;
                    throw var95;
                } finally {
                    if (input != null) {
                        if (var6 != null) {
                            try {
                                input.close();
                            } catch (Throwable var88) {
                                var6.addSuppressed(var88);
                            }
                        } else {
                            input.close();
                        }
                    }

                }
            } catch (Throwable var97) {
                var4 = var97;
                throw var97;
            } finally {
                if (fis != null) {
                    if (var4 != null) {
                        try {
                            fis.close();
                        } catch (Throwable var87) {
                            var4.addSuppressed(var87);
                        }
                    } else {
                        fis.close();
                    }
                }

            }

            long srcLen = srcFile.length();
            long dstLen = destFile.length();
            if (srcLen != dstLen) {
                throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "' Expected length: " + srcLen + " Actual: " + dstLen);
            } else {
                if (preserveFileDate) {
                    destFile.setLastModified(srcFile.lastModified());
                }

            }
        }
    }

    private static void checkFileRequirements(File src, File dest) throws FileNotFoundException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        } else if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!src.exists()) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        }
    }


    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int) count;
    }

    public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, 4096);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count;
        int n;
        for (count = 0L; -1 != (n = input.read(buffer)); count += (long) n) {
            output.write(buffer, 0, n);
        }

        return count;
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length) throws IOException {
        return copyLarge(input, output, inputOffset, length, new byte[4096]);
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer) throws IOException {
        if (inputOffset > 0L) {
            skipFully(input, inputOffset);
        }

        if (length == 0L) {
            return 0L;
        } else {
            int bufferLength = buffer.length;
            int bytesToRead = bufferLength;
            if (length > 0L && length < (long) bufferLength) {
                bytesToRead = (int) length;
            }

            long totalRead = 0L;

            int read;
            while (bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
                output.write(buffer, 0, read);
                totalRead += (long) read;
                if (length > 0L) {
                    bytesToRead = (int) Math.min(length - totalRead, (long) bufferLength);
                }
            }

            return totalRead;
        }
    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int) count;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        return copyLarge(input, output, new char[4096]);
    }

    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        long count;
        int n;
        for (count = 0L; -1 != (n = input.read(buffer)); count += (long) n) {
            output.write(buffer, 0, n);
        }

        return count;
    }

    public static long copyLarge(Reader input, Writer output, long inputOffset, long length) throws IOException {
        return copyLarge(input, output, inputOffset, length, new char[4096]);
    }

    public static long copyLarge(Reader input, Writer output, long inputOffset, long length, char[] buffer) throws IOException {
        if (inputOffset > 0L) {
            skipFully(input, inputOffset);
        }

        if (length == 0L) {
            return 0L;
        } else {
            int bytesToRead = buffer.length;
            if (length > 0L && length < (long) buffer.length) {
                bytesToRead = (int) length;
            }

            long totalRead = 0L;

            int read;
            while (bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
                output.write(buffer, 0, read);
                totalRead += (long) read;
                if (length > 0L) {
                    bytesToRead = (int) Math.min(length - totalRead, (long) buffer.length);
                }
            }

            return totalRead;
        }
    }

    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        if (input1 == input2) {
            return true;
        } else {
            if (!(input1 instanceof BufferedInputStream)) {
                input1 = new BufferedInputStream((InputStream) input1);
            }

            if (!(input2 instanceof BufferedInputStream)) {
                input2 = new BufferedInputStream((InputStream) input2);
            }

            int ch2;
            for (int ch = ((InputStream) input1).read(); -1 != ch; ch = ((InputStream) input1).read()) {
                ch2 = ((InputStream) input2).read();
                if (ch != ch2) {
                    return false;
                }
            }

            ch2 = ((InputStream) input2).read();
            return ch2 == -1;
        }
    }
}
