package com.hdl.words.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Date 2019/4/12 16:36
 * author hdl
 * Description:
 */
public class WordResultBean implements Serializable {
    private static final long serialVersionUID = -264223266421319389L;
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * symbol : /ə'bændən/
         * means :  vt.丢弃；放弃，抛弃
         * id : 1
         * word : abandon
         */
        private static final long serialVersionUID = -26412421319389L;
        private String symbol;
        private String means;
        private int id;
        private String word;

        protected DataBean(Parcel in) {
            symbol = in.readString();
            means = in.readString();
            id = in.readInt();
            word = in.readString();
        }

        /*public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };*/

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getMeans() {
            return means;
        }

        public void setMeans(String means) {
            this.means = means;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

/*        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(symbol);
            dest.writeString(means);
            dest.writeInt(id);
            dest.writeString(word);
        }*/
    }
}
