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

import android.hardware.radio.V1_0.RadioError;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.os.AsyncResult;
import android.os.Message;
import android.telephony.SignalStrength;

import com.android.internal.telephony.RIL;
import com.android.internal.telephony.RadioResponse;

import com.qualcomm.qti.internal.telephony.ExtRIL;

public class ExtRadioResponse extends RadioResponse {

    ExtRIL mExtRIL;

    public ExtRadioResponse(RIL ril) {
        super(ril);
        if (ril instanceof ExtRIL) {
           mExtRIL = (ExtRIL) ril;
        }
    }

    /**
     * Helper function to send response msg
     * @param msg Response message to be sent
     * @param ret Return object to be included in the response message
     */
    static void sendMessageResponse(Message msg, Object ret) {
        if (msg != null) {
            AsyncResult.forMessage(msg, ret, null);
            msg.sendToTarget();
        }
    }

    @Override
    public void getSignalStrengthResponse(RadioResponseInfo responseInfo,
                                          android.hardware.radio.V1_0.SignalStrength sigStrength) {
        if (mExtRIL != null) {
            responseSignalStrength(responseInfo, sigStrength);
        } else {
            super.getSignalStrengthResponse(responseInfo, sigStrength);
        }
    }

    private void responseSignalStrength(RadioResponseInfo responseInfo,
                                        android.hardware.radio.V1_0.SignalStrength sigStrength) {
        Object rr = mExtRIL.processResp(responseInfo);

        if (rr != null) {
            SignalStrength ret = ExtRIL.convertHalSignalStrength(sigStrength, mExtRIL);
            if (responseInfo.error == RadioError.NONE) {
                sendMessageResponse(mExtRIL.getMsgFromRequest(rr), ret);
            }
            mExtRIL.processRespDone(rr, responseInfo, ret);
        }
    }

}
