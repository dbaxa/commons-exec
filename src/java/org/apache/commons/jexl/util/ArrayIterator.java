/*
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Velocity", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.commons.jexl.util;



import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.reflect.Array;


/**
 *  <p>
 *  An Iterator wrapper for an Object[]. This will
 *  allow us to deal with all array like structures
 *  in a consistent manner.
 *  </p>
 *  <p>
 *  WARNING : this class's operations are NOT synchronized.
 *  It is meant to be used in a single thread, newly created
 *  for each use in the #foreach() directive.
 *  If this is used or shared, synchronize in the
 *  next() method.
 *  </p>
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@apache.org">Geir Magnusson Jr.</a>
 * @version $Id: ArrayIterator.java,v 1.1 2002/08/05 05:06:21 geirm Exp $
 */
public class ArrayIterator implements Iterator
{
    /**
     * The objects to iterate.
     */
    private Object array;

    /**
     * The current position and size in the array.
     */
    private int pos;
    private int size;

    /**
     * Creates a new iterator instance for the specified array.
     *
     * @param array The array for which an iterator is desired.
     */
    public ArrayIterator(Object array)
    {
        /*
         * if this isn't an array, then throw.  Note that this is 
         * for internal use - so this should never happen - if it does
         *  we screwed up.
         */
         
        if ( !array.getClass().isArray() )
        {   
            throw new IllegalArgumentException( 
                "Programmer error : internal ArrayIterator invoked w/o array");
        }
            
        this.array = array;
        pos = 0;
        size = Array.getLength( this.array );
    }

    /**
     * Move to next element in the array.
     *
     * @return The next object in the array.
     */
    public Object next()
    {
        if (pos < size )
            return Array.get( array, pos++);
                
        /*
         *  we screwed up...
         */
         
        throw new NoSuchElementException("No more elements: " + pos +
                                         " / " + size);
    }
    
    /**
     * Check to see if there is another element in the array.
     *
     * @return Whether there is another element.
     */
    public boolean hasNext()
    {
        return (pos < size );
    }

    /**
     * No op--merely added to satify the <code>Iterator</code> interface.
     */
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}