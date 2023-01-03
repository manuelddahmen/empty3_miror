/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 *
 *  *  This file is part of Empty3.
 *  *
 *  *     Empty3 is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU General Public License as published by
 *  *     the Free Software Foundation, either version 2 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     Empty3 is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU General Public License
 *  *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 *
 *
 */

package one.empty3.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manue on 17-07-19.
 */
public class History {
    List<RPropertyDetailsRow> history = new ArrayList<>();

    private int current = -1;

    public List<RPropertyDetailsRow> getHistory() {
        return history;
    }

    public void clear() {
        current = -1;
        history.clear();
    }

    public void addToHistory(RPropertyDetailsRow row) {
        current++;
        history.add(current, row);
        if (history.size() >= getCurrent()) {
            for (int i = current + 1; i < history.size(); i++)
                history.remove(i);
        }
    }

    public void setHistory(List<RPropertyDetailsRow> history) {
        this.history = history;
    }


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }


    public void next() {
        if (current < history.size() - 1)
            current++;
    }

    public void back() {
        if (current > 0)
            current--;
    }

    public Object getCurrentRow() {
        if (current < 0)
            current = 0;
        if (current >= history.size()-1)
            current = Math.max(history.size() - 1, 0);

        return history.get(current);
    }

    public RPropertyDetailsRow get(int i) {
        if (current < history.size() - 1)
            if (current >= 0) {
                RPropertyDetailsRow rPropertyDetailsRow = history.get(i);
                return rPropertyDetailsRow;

            }
        return null;
    }
}
