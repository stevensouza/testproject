package com.stevesouza.sigar;

import com.stevesouza.jmx.NumberDelta;
import org.hyperic.sigar.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Note:  I had to add the library location of the sigar lib to the jvm options.  Not sure
 * how this could be done outside of intellij:  -Djava.library.path="/Applications/myapps/sigar/hyperic-sigar-1.6.4/sigar-bin/lib"
 *
 * Note: Sigar has far more methods than I test below.  The following is just representative.
 */
public class SigarTest {

    private static Sigar sigar = new Sigar();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testMem() throws Exception {
        Mem data = sigar.getMem();
        assertThat(data.getActualFree()).isPositive();
        assertThat(data.getActualUsed()).isPositive();
        assertThat(data.getFree()).isPositive();
        assertThat(data.getFreePercent()).isPositive();
        assertThat(data.getRam()).isPositive();
        assertThat(data.getTotal()).isPositive();
        assertThat(data.getUsed()).isPositive();
        assertThat(data.getUsedPercent()).isPositive();
        // {Used=4209500160, ActualUsed=3519660032,
        // FreePercent=18.051528930664062, ActualFree=775307264,
        // Ram=4096, UsedPercent=81.94847106933594,
        // Free=85467136, Total=4294967296}
        System.out.println(data.toMap());
    }

    @Test
    public void testSwap() throws Exception {
        Swap data = sigar.getSwap();
        System.out.println(data.toMap());
        assertThat(data.getUsed()).isPositive();
        assertThat(data.getFree()).isPositive();

    }

    @Test
    public void testCpu() throws Exception {
        Cpu data = sigar.getCpu();
        System.out.println(data.toMap());
    }

    @Test
    public void testCpuPerc() throws Exception {
        CpuPerc data = sigar.getCpuPerc();
        System.out.println(data.toString());
        assertThat(data.getUser()).isNotNegative();
        assertThat(data.getStolen()).isNotNegative();
    }

    @Test
    public void testDiskUsage() throws Exception {
        DiskUsage data = sigar.getDiskUsage("/");
        System.out.println(data.toMap());
        assertThat(data.getReads()).isNotNegative();
        assertThat(data.getReadBytes()).isNotNegative();
        assertThat(data.getWrites()).isNotNegative();
        assertThat(data.getWriteBytes()).isNotNegative();
    }

    @Test
    public void testDirStat() throws Exception {
        DirStat data = sigar.getDirStat("/");
        System.out.println(data.toMap());
        assertThat(data.getTotal()).isNotNegative();
        assertThat(data.getFiles()).isNotNegative();
        assertThat(data.getSubdirs()).isNotNegative();
        assertThat(data.getDiskUsage()).isNotNegative();
    }

    @Test
    public void testFileSystemUsage() throws Exception {
/*
%iused = getUsePercent()
41%=.41

    df -h
    Filesystem      Size   Used  Avail Capacity  iused    ifree %iused  Mounted on
    /dev/disk1     465Gi  190Gi  274Gi    41% 49878167 71920487   41%   /
 */
        FileSystemUsage data = sigar.getFileSystemUsage("/");
        assertThat(data.getUsePercent()).isBetween(0.0,1.0);
        System.out.println(data.toMap());
    }

    @Test
    public void testJdkDiskUsage() throws Exception {
        // http://www.mkyong.com/java/how-to-get-free-disk-space-in-java/
        File file = new File("/");
        long totalSpace = file.getTotalSpace(); //total disk space in bytes.
        long usableSpace = file.getUsableSpace(); ///unallocated / free disk space in bytes. according to javadoc
            // the diff between this method and getFreeSpace is that this one MAY check
            // permissions too.  I think this means some users may not have access
            // to the full free space and this method considers this.
        long freeSpace = file.getFreeSpace(); //unallocated / free disk space in bytes.
        System.out.println("t="+totalSpace+", u="+usableSpace+", f="+freeSpace);
        assertThat(totalSpace).isPositive();
        assertThat(usableSpace).isPositive();
        assertThat(freeSpace).isPositive();
    }

}
