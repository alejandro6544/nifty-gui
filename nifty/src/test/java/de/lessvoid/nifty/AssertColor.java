/*
 * Copyright (c) 2014, Jens Hohmuth 
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are 
 * met: 
 * 
 *  * Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.lessvoid.nifty;

import static org.junit.Assert.assertEquals;
import de.lessvoid.nifty.api.NiftyColor;
import de.lessvoid.nifty.api.NiftyMutableColor;

public class AssertColor {
  public static void assertColor(final double r, final double g, final double b, final double a, final NiftyColor c) {
    assertEquals(r, c.getRed());
    assertEquals(g, c.getGreen());
    assertEquals(b, c.getBlue());
    assertEquals(a, c.getAlpha());
  }

  public static void assertColor(final double r, final double g, final double b, final double a, final NiftyMutableColor c) {
    assertEquals(r, c.getRed());
    assertEquals(g, c.getGreen());
    assertEquals(b, c.getBlue());
    assertEquals(a, c.getAlpha());
  }

  public static void assertColor(final String expected, final NiftyColor c) {
    assertEquals(expected, c.toHexString());
  }
}