package com.gjj.igden.model;

import com.gjj.igden.utils.InstId;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Entity
@Table(name = "market_data")
public class Bar extends MarketData implements  Serializable{
    /**
     * mdId - i suppose this only for bar data_containers because for tick data_containers it is will
     * be impossible to supply all needed id
     */
    private BarSetId id;
    private int dataSetId;
    private int barSize;
    private String ticker;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private String logInfo;
    private Date date;

    public Bar() {
    }

    public Bar(int dataSetId) {
        this.dataSetId = dataSetId;
    }

    public Bar(int dataSetId, int barSize) {
        this.dataSetId = dataSetId;
        this.barSize = barSize;
    }

    public Bar(long mdId) {
        this.id = new BarSetId(mdId);
    }

    public Bar(long mdId, int dataSetId, String instId) {
        this.id = new BarSetId(mdId, instId);
        this.dataSetId = dataSetId;
        this.instId = new InstId(instId);
    }

    public Bar(long mdId, String instId) {
        this.id = new BarSetId(mdId);
        this.instId = new InstId(instId);
    }

    public Bar(int dataSetId, String instId, int barSize) {
        this.dataSetId = dataSetId;
        this.instId = new InstId(instId);
        this.barSize = barSize;
    }

    public Bar(int dataSetId, String instId) {
        this.dataSetId = dataSetId;
        this.instId = new InstId(instId);
    }

    public Bar(Bar bar) {
        super(bar.instId, bar.dateTime);
        this.dataSetId = bar.dataSetId;
        this.id = bar.getId();
        this.barSize = bar.barSize;
        this.date = bar.getDate();
        this.ticker = bar.getTicker();
        this.open = bar.open;
        this.high = bar.high;
        this.low = bar.low;
        this.close = bar.close;
        this.volume = bar.volume;
        this.logInfo = bar.logInfo;
    }

    public Bar(InstId instId, long dateTime, Long mdId, int dataSetId, int barSize, double open,
               double high, double low, double close, long volume, String logInfo) {
        super(instId, dateTime);
        this.id = new BarSetId(mdId);
        this.dataSetId = dataSetId;
        this.barSize = barSize;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.logInfo = logInfo;
    }

    public Bar(
            InstId instId,
            int barSize,
            long dateTime,
            double open,
            double high,
            double low,
            double close) {
        this(instId, barSize, dateTime, open, high, low, close, 0, 0, null);
    }

    public Bar(
            InstId instId,
            int barSize,
            long dateTime,
            double open,
            double high,
            double low,
            double close,
            long volume,
            String logInfo) {
        this(instId, barSize, dateTime, open, high, low, close, volume, 0, logInfo);
    }

    public Bar(
            InstId instId,
            int barSize,
            long dateTime,
            double open,
            double high,
            double low,
            double close,
            long volume,
            long mdId,
            String logInfo) {
        super(instId, dateTime);
        this.id = new BarSetId(mdId);
        this.barSize = barSize;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.logInfo = logInfo;
    }

    @EmbeddedId
    public BarSetId getId() {
        return id;
    }

    public void setId(BarSetId id) {
        this.id = id;
    }

    @Transient
    public int getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(int dataSetId) {
        this.dataSetId = dataSetId;
    }

    @Transient
    public int getBarSize() {
        return barSize;
    }

    public void setBarSize(int barSize) {
        this.barSize = barSize;
    }

    @Column(name = "ticker")
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Column(name = "open")
    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    @Column(name = "high")
    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    @Column(name = "low")
    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    @Column(name =  "close")
    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    @Column(name = "vol")
    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    @Column(name = "additional_info")
    public String getLogInfo() {
        return this.logInfo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        Instant fromUnixTimestamp = Instant.ofEpochSecond(dateTime);
        LocalDateTime time = LocalDateTime.ofInstant(fromUnixTimestamp,
                ZoneId.of("UTC-4"));
        return "\n{ " +
                "mdId=" + this.id.getId() +
                "; instId=" + instId +
                // 	", dateTime=" + dateTime +
                "; dateTime=" + time +
                "; barSize=" + barSize +
                "; high=" + high +
                "; low=" + low +
                "; open=" + open +
                "; close=" + close +
                "; volume=" + volume +
                "; info=" + logInfo +
                " } ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bar bar = (Bar) o;
        return Objects.equal(instId, bar.instId) &&
                Objects.equal(dateTime, bar.dateTime) &&
                // TODO m high priority: the line below - in some moment barSize is not setted up
                // TODO m high priority: use test createDataSetTestImportant() to determine the
                // TODO m high priority: bug source
                //	Objects.equal(barSize, bar.barSize) &&
                Objects.equal(high, bar.high) &&
                Objects.equal(low, bar.low) &&
                Objects.equal(open, bar.open) &&
                Objects.equal(close, bar.close) &&
                Objects.equal(volume, bar.volume) &&
                Objects.equal(this.id, bar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(instId, dateTime, barSize, high, low, open, close, volume, id);
    }

    @Override
    public void reset() {
        super.reset();
        barSize = -1;
        id = null;
        high = 0.0;
        low = 0.0;
        open = 0.0;
        close = 0.0;
        volume = 0;
        logInfo = null;
    }

    public void copy(Bar bar) {
        this.instId = bar.instId;
        this.id = bar.id;
        this.dateTime = bar.dateTime;
        this.barSize = bar.barSize;
        this.high = bar.high;
        this.low = bar.low;
        this.open = bar.open;
        this.close = bar.close;
        this.volume = bar.volume;
        this.logInfo = bar.logInfo;
    }

}
