/*
 * This file is part of Quill by Arcane Arts.
 *
 * Quill by Arcane Arts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Quill by Arcane Arts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License in this package for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quill.  If not, see <https://www.gnu.org/licenses/>.
 */

package art.arcane.quill.tools;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;

@SuppressWarnings("restriction")
public class Platform {
    public static boolean ENABLE = true;
    public static double PROC_CPU = CPU.getLiveProcessCPULoad();

    public static String getVersion() {
        if (!ENABLE) {
            return "?";
        }

        return getSystem().getVersion();
    }

    public static String getName() {
        if (!ENABLE) {
            return "?";
        }

        return getSystem().getName();
    }

    private static OperatingSystemMXBean getSystem() {
        return (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    public static class ENVIRONMENT {
        public static boolean canRunBatch() {
            return getSystem().getName().toLowerCase().contains("windows");
        }

        public static String getJavaHome() {
            if (!ENABLE) {
                return "";
            }

            return System.getProperty("java.home");
        }

        public static String getJavaVendor() {
            if (!ENABLE) {
                return "";
            }

            return System.getProperty("java.vendor");
        }

        public static String getJavaVersion() {
            if (!ENABLE) {
                return "";
            }

            return System.getProperty("java.version");
        }
    }

    public static class STORAGE {
        public static long getAbsoluteTotalSpace() {
            long t = 0;

            for (File i : getRoots()) {
                t += getTotalSpace(i);
            }

            return t;
        }

        public static long getTotalSpace() {
            return getTotalSpace(new File("."));
        }

        public static long getTotalSpace(File root) {
            return root.getTotalSpace();
        }

        public static long getAbsoluteFreeSpace() {
            long t = 0;

            for (File i : getRoots()) {
                t += getFreeSpace(i);
            }

            return t;
        }

        public static long getFreeSpace() {
            return getFreeSpace(new File("."));
        }

        public static long getFreeSpace(File root) {
            return root.getFreeSpace();
        }

        public static long getUsedSpace() {
            return getTotalSpace() - getFreeSpace();
        }

        public static long getUsedSpace(File root) {
            return getTotalSpace(root) - getFreeSpace(root);
        }

        public static long getAbsoluteUsedSpace() {
            return getAbsoluteTotalSpace() - getAbsoluteFreeSpace();
        }

        public static File[] getRoots() {
            return File.listRoots();
        }
    }

    public static class MEMORY {
        public static class PHYSICAL {
            public static long getTotalMemory() {
                if (!ENABLE) {
                    return 0;
                }

                return getSystem().getTotalPhysicalMemorySize();
            }

            public static long getFreeMemory() {
                if (!ENABLE) {
                    return 0;
                }

                return getSystem().getFreePhysicalMemorySize();
            }

            public static long getUsedMemory() {
                if (!ENABLE) {
                    return 0;
                }

                return getTotalMemory() - getFreeMemory();
            }
        }

        public static class VIRTUAL {
            public static long getTotalMemory() {
                if (!ENABLE) {
                    return 0;
                }

                return getSystem().getTotalSwapSpaceSize();
            }

            public static long getFreeMemory() {
                if (!ENABLE) {
                    return 0;
                }

                return getSystem().getFreeSwapSpaceSize();
            }

            public static long getUsedMemory() {
                if (!ENABLE) {
                    return 0;
                }

                return getTotalMemory() - getFreeMemory();
            }

            public static long getCommittedVirtualMemory() {
                if (!ENABLE) {
                    return 0;
                }

                return getSystem().getCommittedVirtualMemorySize();
            }
        }
    }

    public static class CPU {
        public static int getAvailableProcessors() {
            if (!ENABLE) {
                return Runtime.getRuntime().availableProcessors();
            }

            return getSystem().getAvailableProcessors();
        }

        public static double getCPULoad() {
            if (!ENABLE) {
                return 0;
            }

            return getSystem().getSystemCpuLoad();
        }

        public static double getProcessCPULoad() {
            if (!ENABLE) {
                return 0;
            }

            return PROC_CPU;
        }

        public static double getLiveProcessCPULoad() {
            if (!ENABLE) {
                return 0;
            }

            return getSystem().getProcessCpuLoad();
        }

        public static String getArchitecture() {
            if (!ENABLE) {
                return "?";
            }

            return getSystem().getArch();
        }
    }
}
