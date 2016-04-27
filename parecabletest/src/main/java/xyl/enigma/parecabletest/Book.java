package xyl.enigma.parecabletest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 一伦 on 2016/4/2.
 */
public class Book implements Parcelable {
    private String bookName;
    private String author;
    private int publishDate;

    public Book() {

    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(int publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //将Book对象序列化为一个Parcel对象
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(bookName);
        out.writeString(author);
        out.writeInt(publishDate);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }

        ////将Parcel对象反序列化为Book
        @Override
        public Book createFromParcel(Parcel in) {
            Book book = new Book();
            book.bookName = in.readString();
            book.author = in.readString();
            book.publishDate = in.readInt();
            return book;
        }
    };

}
