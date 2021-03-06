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
package com.wildbeeslabs.sensiblemetrics.numeralyzer.utils;

import com.wildbeeslabs.sensiblemetrics.numeralyzer.functions.Filter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Helper class to handle converter operations
 *
 * @author Alex
 * @version 1.0.0
 * @since 2018-11-30
 */
public final class ConverterUtils {

    /**
     * Default logger instance
     */
    private static final Logger LOGGER = LogManager.getLogger(ConverterUtils.class);

    public static final Predicate<Boolean> randomBool = predicate -> new Random().nextBoolean();
    public static final Predicate<String> notEmpty = (String it) -> org.apache.commons.lang3.StringUtils.isNotEmpty(it);
    public static final Predicate<String> notBlank = (String it) -> StringUtils.isNotBlank(it);

    /**
     * Default private constructor
     */
    private ConverterUtils() {
        // PRIVATE EMPTY CONSTRUCTOR
    }

    public static <T> Predicate<T> not(final Predicate<T> t) {
        return t.negate();
    }

    public static <T, K, V> Map<K, V> convertToMap(final Stream<T> stream, final Function<T, K> keys, final Function<T, V> values) {
        return stream.collect(Collectors.toMap(keys, values));
    }

    public static <K, T> Map<K, List<T>> getSortedMapByKey(final Map<K, List<T>> map, final Comparator<? super K> comparator) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(comparator))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static <T> Map<Integer, IntSummaryStatistics> getMapStatisticsBy(final Stream<T> stream, final Function<T, Integer> groupingBy, final ToIntFunction<? super T> mapper) {
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.summarizingInt(mapper)));
    }

    public static <T, K> Map<K, Optional<T>> getMapMaxBy(final Stream<T> stream, final Function<T, K> groupingBy, final Comparator<? super T> cmp) {
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.maxBy(cmp)));
    }

    public static <T, K> Map<K, Optional<T>> getMapMinBy(final Stream<T> stream, final Function<T, K> groupingBy, final Comparator<? super T> cmp) {
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.minBy(cmp)));
    }

    public static <T> Optional<T> getMaxBy(final Stream<T> stream, final Comparator<? super T> cmp) {
        return getMinMaxBy(stream, Collectors.maxBy(cmp));
    }

    public static <T> Optional<T> getMinBy(final Stream<T> stream, final Comparator<? super T> cmp) {
        return getMinMaxBy(stream, Collectors.minBy(cmp));
    }

    protected static <T> Optional<T> getMinMaxBy(final Stream<T> stream, final Collector<T, ?, Optional<T>> collector) {
        return stream.collect(collector);
    }

    public static <E> Map<Integer, Long> getMapCountBy(final Stream<E> stream, final Function<E, Integer> groupingBy) {
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.counting()));
    }

    public static <E, K, U> Map<K, List<U>> convertToMapList(final Stream<E> stream, final Function<E, K> groupingBy, final Function<E, U> mapper) {
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.mapping(mapper, Collectors.toList())));
    }

    public static <E, K, U> Map<K, Set<U>> convertToMapSet(final Stream<E> stream, final Function<E, K> groupingBy, final Function<E, U> mapper) {
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.mapping(mapper, Collectors.toSet())));
    }

    public static <E, U> List<U> convertToList(final Stream<E> stream, final Function<E, U> mapper) {
        return stream.collect(Collectors.mapping(mapper, Collectors.toList()));
    }

    public static <E, U> Stream<U> getStreamBy(final Stream<E> stream, final Function<E, U> mapper, final Predicate<U> predicate) {
        return stream.map(mapper).filter(predicate);
    }

    public static <T extends Number> T reduceStreamBy(final Stream<T> stream, final T identity, final BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    public static <E> Stream<E> getStreamSortedBy(final Stream<E> stream, final Comparator<? super E> cmp) {
        return stream.sorted(cmp);
    }

    public static <T, K> Map<K, Integer> getMapSumBy(final Stream<T> stream, final Function<T, K> keys, final Function<T, Integer> values) {
        return stream.collect(Collectors.toMap(keys, values, Integer::sum));
    }

    public static <A, B, C> Function<A, C> compose(Function<A, B> f1, Function<B, C> f2) {
        return f1.andThen(f2);
    }

    public static <T> Collection<T> join(final Collection<T> first, final Collection<T> second, final Predicate<? super T> predicate) {
        return Stream.concat(first.stream(), second.stream()).filter(predicate).collect(Collectors.toList());
    }

    public static <T> String join(final Collection<T> collection, final String delimiter) {
        return collection.stream().map(Objects::toString).collect(Collectors.joining(delimiter));
    }

    public static <K, V> String join(final Map<K, V> map, final String keyValueDelimiter, final String delimiter) {
        return map.entrySet().stream().map(entry -> entry.getKey() + keyValueDelimiter + entry.getValue()).collect(Collectors.joining(delimiter));
    }

    public static <T> String[] join(final T[] first, final T[] second) {
        return Stream.concat(Arrays.stream(first), Arrays.stream(second)).toArray(String[]::new);
    }

    public static <T> String join(final T[] array, final String delimiter) {
        return Arrays.stream(array).map(Objects::toString).collect(Collectors.joining(delimiter));
    }

    public static <T> String joinWithPrefixPostfix(final T[] array, final String delimiter, final String prefix, final String postfix) {
        return joinWithPrefixPostfix(Arrays.asList(array), delimiter, prefix, postfix);
    }

    public static <T> String joinWithPrefixPostfix(final Collection<T> list, final String delimiter, final String prefix, final String postfix) {
        return list.stream().map(Objects::toString).collect(Collectors.joining(delimiter, prefix, postfix));
    }

    public static Map<Integer, List<String>> getMapByLength(final String[] array) {
        return Arrays.stream(array).filter(Objects::nonNull).collect(Collectors.groupingBy(String::length));
    }

    public static String[] getArrayBy(final String[] array, final Predicate<? super String> predicate) {
        return getArrayBy(Arrays.asList(array), predicate);
    }

    public static String[] getArrayBy(final List<String> list, final Predicate<? super String> predicate) {
        return list.stream().filter(predicate).toArray(size -> new String[size]);
    }

    public static List<String> split(final String value, final String delimiter) {
        return split(value, delimiter, (str) -> Boolean.TRUE);
    }

    public static List<String> split(final String value, final String delimiter, final Predicate<? super String> predicate) {
        return Arrays.stream(String.valueOf(value).split(delimiter))
                .map(String::trim)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static List<Character> splitToListOfChars(final String value) {
        return value.chars()
                .mapToObj(item -> (char) item)
                .collect(Collectors.toList());
    }

    public static <T> List<? extends T> convertToList(final T[] array, final IntPredicate indexPredicate) {
        return convertTo(array, indexPredicate, Collectors.toList());
    }

    public static <T, M> M convertTo(final T[] array, final IntPredicate indexPredicate, final Collector<T, ?, M> collector) {
        return IntStream
                .range(0, array.length)
                .filter(indexPredicate)
                .mapToObj(i -> array[i])
                .collect(collector);
    }

    public static <T> Collector<T, ?, List<T>> lastN(int n) {
        assert (n > 0);
        return Collector.<T, Deque<T>, List<T>>of(ArrayDeque::new, (acc, t) -> {
            if (acc.size() == n) {
                acc.pollFirst();
            }
            acc.add(t);
        }, (acc1, acc2) -> {
            while (acc2.size() < n && !acc1.isEmpty()) {
                acc2.addFirst(acc1.pollLast());
            }
            return acc2;
        }, ArrayList::new);
    }

    public static <T> Collector<T, ?, LinkedList<T>> toLinkedList() {
        return Collector.of(LinkedList::new, LinkedList::add,
                (first, second) -> {
                    first.addAll(second);
                    return first;
                });
    }

    public static <T> String replace(final String input, final Pattern regex, final Function<Matcher, String> function) {
        final StringBuffer resultString = new StringBuffer();
        final Matcher regexMatcher = regex.matcher(input);
        while (regexMatcher.find()) {
            regexMatcher.appendReplacement(resultString, function.apply(regexMatcher));
        }
        regexMatcher.appendTail(resultString);
        return resultString.toString();
    }

    public static <E> Stream<E> getFilteredStream(final Stream<E> stream, final Filter<CharSequence, CharSequence> tokenFilter, final String tokenDelim) {
        return stream.flatMap(line -> Arrays.stream(String.valueOf(line).trim().split(tokenDelim)))
                .map(item -> tokenFilter.apply(item))
                .filter(StringUtils::isNotBlank)
                .map(item -> (E) item);
    }

    public static <T> List<T> convert(final List<Optional<T>> listOfOptionals) {
        Objects.requireNonNull(listOfOptionals);
        return listOfOptionals.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public static <T> List<Stream<T>> convertFlat(final List<Optional<T>> listOfOptionals) {
        Objects.requireNonNull(listOfOptionals);
        return (List<Stream<T>>) listOfOptionals.stream()
                .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                .collect(Collectors.toList());
    }
}