// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.datamasking.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.dataquality.duplicating.RandomWrapper;

/**
 * created by jgonzalez on 30 juin 2015 Detailled comment
 *
 */
public class KeepFirstCharsLongTest {

    private String output;

    private Long input = 123456L;

    private KeepFirstCharsLong kfag = new KeepFirstCharsLong();

    @Test
    public void testGood() {
        kfag.parse("3", false, new RandomWrapper(42));
        output = kfag.generateMaskedRow(input).toString();
        assertEquals(output, "123830"); //$NON-NLS-1$
    }

    @Test
    public void testDummyGood() {
        kfag.parse("7", false, new RandomWrapper(42));
        output = kfag.generateMaskedRow(input).toString();
        assertEquals(output, input.toString());
    }

    @Test
    public void testParameters() {
        kfag.parse("2,6", false, new RandomWrapper(42));
        output = kfag.generateMaskedRow(input).toString();
        assertEquals(output, "126666");
    }
}