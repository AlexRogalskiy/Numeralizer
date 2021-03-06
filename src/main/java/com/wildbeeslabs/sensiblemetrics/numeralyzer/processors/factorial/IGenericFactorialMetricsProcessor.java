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
package com.wildbeeslabs.sensiblemetrics.numeralyzer.processors.factorial;

import com.wildbeeslabs.sensiblemetrics.numeralyzer.metrics.factorial.IFactorialMetrics;
import com.wildbeeslabs.sensiblemetrics.numeralyzer.processors.IGenericMetricsProcessor;

/**
 * Generic factorial metrics processor declaration
 *
 * @param <T> - {@link Object}
 * @param <E> - {@link Object}
 * @param <R> - {@link IFactorialMetrics}
 * @author alexander.rogalskiy
 * @version 1.0
 * @since 2018-11-30
 */
public interface IGenericFactorialMetricsProcessor<T, E, R extends IFactorialMetrics<T, E>> extends IGenericMetricsProcessor<T, E, R> {

    /**
     * Returns number of trailing zeros by input factorized value
     *
     * @param value - input value to be processed
     * @return number of trailing zeros
     */
    E countTrailingZeros(final T value);
}
