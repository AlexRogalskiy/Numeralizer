/*
 * The MIT License
 *
 * Copyright 2018 WildBees Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.numeralyzer.metrics.lexical;

import com.wildbeeslabs.sensiblemetrics.numeralyzer.entities.IGenericLexicalToken;
import com.wildbeeslabs.sensiblemetrics.numeralyzer.entities.IGenericLexicalTokenTerm;
import com.wildbeeslabs.sensiblemetrics.numeralyzer.metrics.IGenericMetrics;

import java.util.IntSummaryStatistics;

/**
 * Lexical token term metrics interface declaration
 *
 * @param <S> - {@link CharSequence}
 * @param <M> - {@link IGenericLexicalToken}
 * @param <T> - {@link IGenericLexicalTokenTerm}
 * @param <E> - {@link Object}
 * @author alexander.rogalskiy
 * @version 1.0
 * @since 2018-11-30
 */
public interface ILexicalTokenTermMetrics<S extends CharSequence, M extends IGenericLexicalToken<S>, T extends IGenericLexicalTokenTerm<S, M>, E> extends IGenericMetrics<T, E> {

    /**
     * Returns statistics of the input term
     *
     * @param value - input term
     * @return - term statistics
     */
    IntSummaryStatistics getStatistics(final T value);

    /**
     * Returns average tokens length of the input term
     *
     * @param value - input term
     * @return average tokens length
     */
    double getAverageTokenLength(final T value);
}