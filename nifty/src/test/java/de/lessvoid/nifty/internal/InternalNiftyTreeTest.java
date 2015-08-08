/*
 * Copyright (c) 2015, Nifty GUI Community
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
package de.lessvoid.nifty.internal;

import de.lessvoid.nifty.api.NiftyNodeLong;
import de.lessvoid.nifty.api.NiftyNodeString;
import de.lessvoid.nifty.api.NiftyRuntimeException;
import de.lessvoid.nifty.api.node.NiftyNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static de.lessvoid.nifty.api.NiftyNodeLong.niftyNodeLong;
import static de.lessvoid.nifty.api.NiftyNodeString.niftyNodeString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by void on 23.07.15.
 */
public class InternalNiftyTreeTest {
  private InternalNiftyTree tree;

  @Test(expected = NiftyRuntimeException.class)
  public void testNullRootNode() {
    createTree(null);
  }

  @Test
  public void testGetRootNode() {
    createTree(niftyNodeString("root"));
    assertEquals(niftyNodeString("root"), tree.getRootNode());
  }

  @Test
  public void testAddSingleChildToRoot() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("child"));
    assertTree(
        "root",
        "  child");
  }

  @Test
  public void testAddMultipleChildsToRoot() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeString("c2-1"));
    assertTree(
        "root",
        "  c1",
        "  c2",
        "    c2-1",
        "  c3",
        "  c4");
  }

  @Test
  public void testAddChildsToParent() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"))
        .addChild(niftyNodeString("c1"), niftyNodeString("c2"));
    assertTree(
        "root",
        "  c1",
        "    c2");
  }

  @Test(expected = NiftyRuntimeException.class)
  public void testRemoveRoot() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"))
        .addChild(niftyNodeString("c1"), niftyNodeString("c2"));
    tree.remove(niftyNodeString("root"));
  }

  @Test
  public void testRemoveLastChild() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"))
        .addChild(niftyNodeString("c1"), niftyNodeString("c2"));
    tree.remove(niftyNodeString("c2"));
    assertTree(
        "root",
        "  c1");
  }

  @Test
  public void testRemoveMiddleChild() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"))
        .addChild(niftyNodeString("c1"), niftyNodeString("c2"));
    tree.remove(niftyNodeString("c1"));
    assertTree(
        "root",
        "  c2");
  }

  @Test(expected = NiftyRuntimeException.class)
  public void testRemoveNoneExistingChild() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"))
        .addChild(niftyNodeString("c1"), niftyNodeString("c2"));
    tree.remove(niftyNodeString("c1"));
    tree.remove(niftyNodeString("c1"));
  }

  @Test
  public void testChildNodes() {
    createTree(niftyNodeString("root"));
    assertChildNodes(niftyNodeString("root"));
  }

  @Test
  public void testChildNodesSingleChild() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("child"));
    assertChildNodes(niftyNodeString("root"), niftyNodeString("child"));
  }

  @Test
  public void testChildNodesMultipleChilds() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeString("c2-1"));
    assertChildNodes(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c2-1"), niftyNodeString("c3"), niftyNodeString("c4"));
  }

  @Test
  public void testChildNodesMultipleChildsAfterRemove() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeString("c2-1"));
    tree.remove(niftyNodeString("c1"));
    tree.remove(niftyNodeString("c2-1"));
    tree.remove(niftyNodeString("c3"));
    assertChildNodes(niftyNodeString("root"), niftyNodeString("c2"), niftyNodeString("c4"));
  }

  @Test
  public void testChildNodesFromSpecificParentRoot() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeString("c2-1"));
    assertChildNodesFromParent(niftyNodeString("root"), niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c2-1"), niftyNodeString("c3"), niftyNodeString("c4"));
  }

  @Test
  public void testChildNodesFromSpecificParent() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeString("c2-1"));
    assertChildNodesFromParent(niftyNodeString("c2"), niftyNodeString("c2"), niftyNodeString("c2-1"));
  }

  @Test
  public void testDifferentChildNodeTypes() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeLong(46L));
    assertTree(
        "root",
        "  c1",
        "  c2",
        "    46",
        "  c3",
        "  c4");
  }

  @Test
  public void testDifferentChildNodeTypesFilteredTestLongNode() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeLong(46L));
    assertFilteredChildNodes(NiftyNodeLong.class, niftyNodeLong(46L));
  }

  @Test
  public void testDifferentChildNodeTypesFilteredTestStringNode() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeLong(46L));
    assertFilteredChildNodes(NiftyNodeString.class, niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"));
  }

  @Test
  public void testDifferentChildNodeTypesFilteredTestLongNodeFromParent() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeLong(46L));
    assertFilteredChildNodesFromParent(NiftyNodeLong.class, niftyNodeString("c2"), niftyNodeLong(46L));
  }

  @Test
  public void testDifferentChildNodeTypesFilteredTestStringNodeFromParent() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeLong(46L));
    assertFilteredChildNodesFromParent(NiftyNodeString.class, niftyNodeString("c2"), niftyNodeString("c2"));
  }

  @Test
  public void testGetParentOfRootNode() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeLong(46L));
    assertNull(tree.getParent(niftyNodeString("root")));
  }

  @Test
  public void testGetParentOfNiftyNode() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeString("c1"), niftyNodeString("c2"), niftyNodeString("c3"), niftyNodeString("c4"))
        .addChild(niftyNodeString("c2"), niftyNodeLong(46L));
    assertEquals(niftyNodeString("root"), tree.getParent(niftyNodeString("c1")));
    assertEquals(niftyNodeString("c2"), tree.getParent(niftyNodeLong(46L)));
  }

  @Test
  public void testGetParentOfNiftyNodeFiltered() {
    tree = createTree(niftyNodeString("root"))
        .addChild(niftyNodeString("root"), niftyNodeLong(46L))
        .addChild(niftyNodeLong(46L), niftyNodeString("c2"))
        .addChild(niftyNodeString("c2"), niftyNodeString("46"));
    assertEquals(niftyNodeLong(46L), tree.getParent(NiftyNodeLong.class, niftyNodeString("46")));
  }

  private InternalNiftyTree createTree(final NiftyNodeString root) {
    tree = new InternalNiftyTree(root);
    return tree;
  }

  private void assertTree(final String ... expected) {
    assertEquals(buildExpected(expected), tree.toString());
  }

  private String buildExpected(final String[] expected) {
    StringBuilder result = new StringBuilder();
    for (int i=0; i<expected.length; i++) {
      result.append(expected[i]);
      if (i < expected.length - 1) {
        result.append("\n");
      }
    }
    return result.toString();
  }

  private void assertChildNodes(final NiftyNodeString... expected) {
    assertEqualList(makeList(tree.childNodes()), expected);
  }

  private void assertChildNodesFromParent(final NiftyNodeString parent, final NiftyNodeString... expected) {
    assertEqualList(makeList(tree.childNodes(parent)), expected);
  }

  private <X extends NiftyNode> void assertFilteredChildNodes(final Class<X> clazz, final X ... expected) {
    assertEqualList(makeList(tree.filteredChildNodes(clazz)), expected);
  }

  private <X extends NiftyNode> void assertFilteredChildNodesFromParent(final Class<X> clazz, final NiftyNode parent, final X... expected) {
    assertEqualList(makeList(tree.filteredChildNodes(clazz, parent)), expected);
  }

  private <X> void assertEqualList(final List<X> actual, final X... expected) {
    assertEquals(expected.length, actual.size());
    for (int i=0; i<expected.length; i++) {
      assertEquals(expected[i], actual.get(i));
    }
  }

  private static <E> List<E> makeList(final Iterable<E> iter) {
    List<E> list = new ArrayList<>();
    for (E item : iter) {
      list.add(item);
    }
    return list;
  }
}