/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2021
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.drg;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
/**
 * Junit Test for selectionMenuFunction
 * @author zkanris
 *
 */
class TestDrgSim {

    @Test
    void selectionMenuTest() throws Exception {

        // Testing Private Member function
        DrgSimulatorApplication application = new DrgSimulatorApplication();
        Method method = DrgSimulatorApplication.class.getDeclaredMethod("selectionMenu", int.class);
        method.setAccessible(true);

        Integer result = (Integer) method.invoke(application, 6);
        assertEquals(0, result);

    }

}
