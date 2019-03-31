package com.match.app.message.entity;

import java.util.List;

public class TimeInterval {

    private List<Interval> result;

    public class Interval {
        private String time;
        private String timeStart;
        private String timeEnd;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }
    }

    public List<Interval> getResult() {
        return result;
    }

    public void setResult(List<Interval> result) {
        this.result = result;
    }
}
