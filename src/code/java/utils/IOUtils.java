package code.java.utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

//部分API来源common io
public class IOUtils {
    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(Reader input) {
        closeQuietly((Closeable) input);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(Writer output) {
        closeQuietly((Closeable) output);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(InputStream input) {
        closeQuietly((Closeable) input);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(OutputStream output) {
        closeQuietly((Closeable) output);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException var2) {
        }

    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(Closeable... closeables) {
        if (closeables != null) {
            Closeable[] var1 = closeables;
            int var2 = closeables.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Closeable closeable = var1[var3];
                closeQuietly(closeable);
            }

        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException var2) {
            }
        }

    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException var2) {
            }
        }

    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void closeQuietly(ServerSocket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException var2) {
            }
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

    /**
     * @deprecated
     */
    @Deprecated
    public static void copy(InputStream input, Writer output) throws IOException {
        copy(input, output, Charset.defaultCharset());
    }

    public static void copy(InputStream input, Writer output, Charset inputEncoding) throws IOException {
        InputStreamReader in = new InputStreamReader(input, Charsets.toCharset(inputEncoding));
        copy((Reader) in, (Writer) output);
    }

    public static void copy(InputStream input, Writer output, String inputEncoding) throws IOException {
        copy(input, output, Charsets.toCharset(inputEncoding));
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

    /**
     * @deprecated
     */
    @Deprecated
    public static void copy(Reader input, OutputStream output) throws IOException {
        copy(input, output, Charset.defaultCharset());
    }

    public static void copy(Reader input, OutputStream output, Charset outputEncoding) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output, Charsets.toCharset(outputEncoding));
        copy((Reader) input, (Writer) out);
        out.flush();
    }

    public static void copy(Reader input, OutputStream output, String outputEncoding) throws IOException {
        copy(input, output, Charsets.toCharset(outputEncoding));
    }

    private static byte[] SKIP_BYTE_BUFFER;

    public static long skip(InputStream input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        } else {
            if (SKIP_BYTE_BUFFER == null) {
                SKIP_BYTE_BUFFER = new byte[2048];
            }

            long remain;
            long n;
            for (remain = toSkip; remain > 0L; remain -= n) {
                n = (long) input.read(SKIP_BYTE_BUFFER, 0, (int) Math.min(remain, 2048L));
                if (n < 0L) {
                    break;
                }
            }

            return toSkip - remain;
        }
    }

    public static long skip(ReadableByteChannel input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        } else {
            ByteBuffer skipByteBuffer = ByteBuffer.allocate((int) Math.min(toSkip, 2048L));

            long remain;
            int n;
            for (remain = toSkip; remain > 0L; remain -= (long) n) {
                skipByteBuffer.position(0);
                skipByteBuffer.limit((int) Math.min(remain, 2048L));
                n = input.read(skipByteBuffer);
                if (n == -1) {
                    break;
                }
            }

            return toSkip - remain;
        }
    }

    public static long skip(Reader input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        } else {
            char[] SKIP_CHAR_BUFFER = new char[2048];
            long remain;
            long n;
            for (remain = toSkip; remain > 0L; remain -= n) {
                n = (long) input.read(SKIP_CHAR_BUFFER, 0, (int) Math.min(remain, 2048L));
                if (n < 0L) {
                    break;
                }
            }

            return toSkip - remain;
        }
    }

    public static void skipFully(InputStream input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        } else {
            long skipped = skip(input, toSkip);
            if (skipped != toSkip) {
                throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
            }
        }
    }

    public static void skipFully(ReadableByteChannel input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        } else {
            long skipped = skip(input, toSkip);
            if (skipped != toSkip) {
                throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
            }
        }
    }

    public static void skipFully(Reader input, long toSkip) throws IOException {
        long skipped = skip(input, toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Chars to skip: " + toSkip + " actual: " + skipped);
        }
    }
}


class Charsets {
    /**
     * @deprecated
     */
    @Deprecated
    public static final Charset ISO_8859_1;
    /**
     * @deprecated
     */
    @Deprecated
    public static final Charset US_ASCII;
    /**
     * @deprecated
     */
    @Deprecated
    public static final Charset UTF_16;
    /**
     * @deprecated
     */
    @Deprecated
    public static final Charset UTF_16BE;
    /**
     * @deprecated
     */
    @Deprecated
    public static final Charset UTF_16LE;
    /**
     * @deprecated
     */
    @Deprecated
    public static final Charset UTF_8;

    public Charsets() {
    }

    public static SortedMap<String, Charset> requiredCharsets() {
        TreeMap<String, Charset> m = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        m.put(StandardCharsets.ISO_8859_1.name(), StandardCharsets.ISO_8859_1);
        m.put(StandardCharsets.US_ASCII.name(), StandardCharsets.US_ASCII);
        m.put(StandardCharsets.UTF_16.name(), StandardCharsets.UTF_16);
        m.put(StandardCharsets.UTF_16BE.name(), StandardCharsets.UTF_16BE);
        m.put(StandardCharsets.UTF_16LE.name(), StandardCharsets.UTF_16LE);
        m.put(StandardCharsets.UTF_8.name(), StandardCharsets.UTF_8);
        return Collections.unmodifiableSortedMap(m);
    }

    public static Charset toCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    public static Charset toCharset(String charset) {
        return charset == null ? Charset.defaultCharset() : Charset.forName(charset);
    }

    static {
        ISO_8859_1 = StandardCharsets.ISO_8859_1;
        US_ASCII = StandardCharsets.US_ASCII;
        UTF_16 = StandardCharsets.UTF_16;
        UTF_16BE = StandardCharsets.UTF_16BE;
        UTF_16LE = StandardCharsets.UTF_16LE;
        UTF_8 = StandardCharsets.UTF_8;
    }
}
