/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.utils.excel.write;

import com.example.demo.utils.excel.ExcelProcessController;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author <a href="mailto:hellojavaer@gmail.com">zoukaiming</a>
 */
public interface ExcelWriteRowProcessor<T> {

    /**
     * 
     * @param controller
     * @param context
     * @param t
     * @param row
     * @return
     */
    void process(ExcelProcessController controller, ExcelWriteContext<T> context, T t, Row row);

}
