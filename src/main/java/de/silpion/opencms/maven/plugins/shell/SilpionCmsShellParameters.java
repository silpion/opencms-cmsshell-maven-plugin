package de.silpion.opencms.maven.plugins.shell;

/*-
 * #%L
 * OpenCms CmsShell Maven Plugin
 * %%
 * Copyright (C) 2017 - 2018 Silpion IT-Solutions GmbH (https://www.silpion.de/)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.io.PrintStream;
import java.util.Objects;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class SilpionCmsShellParameters {
    private static SilpionCmsShellParameters instance = new SilpionCmsShellParameters();

    private PrintStream out;
    private PrintStream err;

    public static SilpionCmsShellParameters getInstance() {
        return instance;
    }

    public static void init(PrintStream out, PrintStream err) {
        instance.out = Objects.requireNonNull(out, "out");
        instance.err = Objects.requireNonNull(err, "err");;
    }

    public PrintStream getOut() {
        return out;
    }

    public PrintStream getErr() {
        return err;
    }
}
