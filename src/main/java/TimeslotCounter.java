/*
 * Copyright 2018 InfAI (CC SES)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.infai.seits.sepl.operators.Message;
import org.infai.seits.sepl.operators.OperatorInterface;

import java.util.HashMap;

public class TimeslotCounter implements OperatorInterface {

    HashMap<String, int[]> map;

    public TimeslotCounter() {
        map = new HashMap<>();
    }

    @Override
    public void run(Message message) {
        int[] timeCounts;

        long newTime;

        newTime = DateParser.parseDateMills(message.getInput("timestamp").getString());
        String deviceID = message.getInput("device").getString();
        if (!map.containsKey(deviceID)) {
            //Setup first item

            timeCounts = new int[24];
        }else{
            timeCounts = map.get(deviceID);
        }
        int hourOfDay = (int) (newTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //millies mod millies-per-day div millies-per-hour
        timeCounts[hourOfDay]++;

        map.put(deviceID, timeCounts);

        String s = "["
                + timeCounts[0] + ","
                + timeCounts[1] + ","
                + timeCounts[2] + ","
                + timeCounts[3] + ","
                + timeCounts[4] + ","
                + timeCounts[5] + ","
                + timeCounts[6] + ","
                + timeCounts[7] + ","
                + timeCounts[8] + ","
                + timeCounts[9] + ","
                + timeCounts[10] + ","
                + timeCounts[11] + ","
                + timeCounts[12] + ","
                + timeCounts[13] + ","
                + timeCounts[14] + ","
                + timeCounts[15] + ","
                + timeCounts[16] + ","
                + timeCounts[17] + ","
                + timeCounts[18] + ","
                + timeCounts[19] + ","
                + timeCounts[20] + ","
                + timeCounts[21] + ","
                + timeCounts[22] + ","
                + timeCounts[23] + ","
                + "]";
        message.output("timeCounts", s);

    }

    @Override
    public void config(Message message) {
        message.addInput("device");
        message.addInput("timestamp");
    }
}
