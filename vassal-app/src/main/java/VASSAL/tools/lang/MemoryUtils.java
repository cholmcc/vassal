/*
 *
 * Copyright (c) 2009-2013 by Joel Uckelman
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License (LGPL) as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, copies are available
 * at http://www.opensource.org.
 */
package VASSAL.tools.lang;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class for getting information about system memory.
 */
public class MemoryUtils {
  protected MemoryUtils() {}

  private static final Logger logger = LoggerFactory.getLogger(MemoryUtils.class);

  /**
   * Gets the amount of physical memory (RAM) in this machine, in bytes.
   *
   * @return the amount of RAM, in bytes; or -1 if the amount of RAM
   * cannot be queried.
   */
  public static long getPhysicalMemory() {
    try {
      final Object o = ManagementFactory.getOperatingSystemMXBean();
      if (o instanceof OperatingSystemMXBean) {
        final OperatingSystemMXBean osb = (OperatingSystemMXBean) o;
        return osb.getTotalPhysicalMemorySize();
      }
    }
    catch (Exception e) {
      // There can be unchecked exceptions due to bugs in Java, argh.
      logger.error("Failed to get amount of physical RAM", e);
    }

    // We didn't get a com.sun.management.OperatingSystemMXBean.
    return -1;
  }

  public static void main(String[] args) {
    final long ram = getPhysicalMemory();
    if (ram >= 0) {
      System.out.println("System reports " + (ram >> 20) + "MB RAM"); //NON-NLS
    }
    else {
      System.out.println("Could not determine amount of RAM"); //NON-NLS
    }
  }
}
