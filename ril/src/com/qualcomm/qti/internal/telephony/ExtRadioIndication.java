/*
 * Copyright (C) 2018 The LineageOS Project
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

package com.qualcomm.qti.internal.telephony;

import android.os.AsyncResult;
import android.telephony.SignalStrength;

import com.android.internal.telephony.RIL;
import com.android.internal.telephony.RadioIndication;

import com.qualcomm.qti.internal.telephony.ExtRIL;

public class ExtRadioIndication extends RadioIndication {

    ExtRIL mExtRIL;

    public ExtRadioIndication(RIL ril) {
        super(ril);
        if (ril instanceof ExtRIL) {
           mExtRIL = (ExtRIL) ril;
        }
    }

    @Override
    public void currentSignalStrength(int indicationType,
                                      android.hardware.radio.V1_0.SignalStrength signalStrength) {
        if (mExtRIL != null) {
            SignalStrength ss = ExtRIL.convertHalSignalStrength(signalStrength, mExtRIL);
            // Note this is set to "verbose" because it happens frequently
            // This is always false, so just comment it out
            //if (RIL.RILJ_LOGV) mExtRIL.unsljLogvRet(RIL_UNSOL_SIGNAL_STRENGTH, ss);

            if (mExtRIL.getSignalStrengthRegistrant() != null) {
                mExtRIL.getSignalStrengthRegistrant().notifyRegistrant(new AsyncResult (null, ss, null));
            }
        } else {
            super.currentSignalStrength(indicationType, signalStrength);
        }
    }

}
