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
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package one.empty3.apps.opad;

import javax.swing.*;
import java.awt.*;

/*__
 * Created by Win on 26-10-18.
 */
public class NewPlayerDialog extends JDialog {
    public String playerName;

    public NewPlayerDialog(Frame owner, boolean modal) {
        super(owner, modal);



        FlowLayout flowLayout = new FlowLayout();

        flowLayout.setHgap(3);

        setLayout(flowLayout);
        JLabel jLabel = new JLabel("Player name");
        jLabel.setSize(100,20);
        add(jLabel);
        JTextField jTextFieldNewPlayer = new JTextField("");
        jTextFieldNewPlayer.setSize(100,20);
        add(jTextFieldNewPlayer);

        JButton ok = new JButton("Ok");
        ok.setSize(100,20);

        add(ok);

        ok.addActionListener(e -> {
            playerName = jTextFieldNewPlayer.getText();

            new Player(playerName, Color.cyan, 100).save();
        });

        pack();
    }



}
