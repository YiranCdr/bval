/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.bval.jsr.util;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Utility {@link Collector} definitions.
 */
public class ToUnmodifiable {

    /**
     * Collector to unmodifiable {@link Set} with custom backing implementation.
     * 
     * @param set
     *            {@link Supplier}
     * @return {@link Collector}
     */
    public static <T> Collector<T, ?, Set<T>> set(Supplier<Set<T>> set) {
        return Collectors.collectingAndThen(Collectors.toCollection(set),
            t -> t.isEmpty() ? Collections.emptySet() : Collections.unmodifiableSet(t));
    }

    /**
     * Collector to unmodifiable {@link Set} (maintains insertion order).
     * 
     * @return {@link Collector}
     */
    public static <T> Collector<T, ?, Set<T>> set() {
        return set(LinkedHashSet::new);
    }

    /**
     * Collector to unmodifiable {@link List}.
     * 
     * @return {@link Collector}
     */
    public static <T> Collector<T, ?, List<T>> list() {
        return Collectors.collectingAndThen(Collectors.toList(),
            t -> t.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(t));
    }

    /**
     * Collector to unmodifiable {@link Map}.
     * 
     * @param keyMapper
     * @param valueMapper
     * @return {@link Collector}
     */
    public static <T, K, U> Collector<T, ?, Map<K, U>> map(Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends U> valueMapper) {
        return Collectors.collectingAndThen(Collectors.toMap(keyMapper, valueMapper),
            t -> t.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(t));
    }
}
