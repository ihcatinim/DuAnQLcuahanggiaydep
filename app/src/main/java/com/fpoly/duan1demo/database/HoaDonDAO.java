package com.fpoly.duan1demo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.duan1demo.object.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    public static final String TABLE_NAME = "HoaDon";
    public static final String SQL_HOADON = "CREATE TABLE HoaDon (" +
            "maHoaDon INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ngayBan DATE NOT NULL," +
            "tenKH TEXT NOT NULL," +
            "chietKhau TEXT NOT NULL," +
            "khachTra NUMBER NOT NULL," +
            "traLai NUMBER NOT NULL," +
            "tongTien NUMBER NOT NULL," +
            "trangThai TEXT NOT NULL)";

    @SuppressLint("SimpleDateFormat")
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SQLiteDatabase sqLiteDatabase;

    public HoaDonDAO(Context context) {
        Mydatabase mydatabase = new Mydatabase(context);
        sqLiteDatabase = mydatabase.getWritableDatabase();
    }

    public long addHoaDon(HoaDon hoaDon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngayBan", simpleDateFormat.format(hoaDon.getNgayBan()));
        contentValues.put("tenKH", hoaDon.getTenKH());
        contentValues.put("chietKhau", hoaDon.getChietKhau());
        contentValues.put("khachTra", hoaDon.getKhachTra());
        contentValues.put("traLai", hoaDon.getTraLai());
        contentValues.put("tongTien", hoaDon.getTongTien());
        contentValues.put("trangThai", hoaDon.getTrangThai());
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public long updateHoaDon(HoaDon hoaDon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngayBan", simpleDateFormat.format(hoaDon.getNgayBan()));
        contentValues.put("tenKH", hoaDon.getTenKH());
        contentValues.put("chietKhau", hoaDon.getChietKhau());
        contentValues.put("khachTra", hoaDon.getKhachTra());
        contentValues.put("traLai", hoaDon.getTraLai());
        contentValues.put("tongTien", hoaDon.getTongTien());
        contentValues.put("trangThai", hoaDon.getTrangThai());
        return sqLiteDatabase.update(TABLE_NAME, contentValues, "maHoaDon = ?", new String[]{String.valueOf(hoaDon.getMaHD())});
    }

    public long deleteHoaDon(int maHd) {
        return sqLiteDatabase.delete(TABLE_NAME, "maHoaDon = ?", new String[]{String.valueOf(maHd)});
    }

    public List<HoaDon> getAllHoaDon() throws ParseException {
        List<HoaDon> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int maHD = Integer.parseInt(cursor.getString(0));
                String ngayBan = cursor.getString(1);
                String tenKh = cursor.getString(2);
                int chietKhau = cursor.getInt(3);
                int khachTra = cursor.getInt(4);
                int traLai = cursor.getInt(5);
                int tongTien = cursor.getInt(6);
                String trangThai = cursor.getString(7);
                HoaDon hoaDon = new HoaDon(maHD, tenKh, chietKhau, khachTra, traLai, tongTien, simpleDateFormat.parse(ngayBan), trangThai);
                list.add(hoaDon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public List<HoaDon> getAllHoaDonTheoMa(String ma) throws ParseException {
        List<HoaDon> list = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME + " where maHoaDon like '%" + ma + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int maHD = Integer.parseInt(cursor.getString(0));
                String ngayBan = cursor.getString(1);
                String tenKh = cursor.getString(2);
                int chietKhau = cursor.getInt(3);
                int khachTra = cursor.getInt(4);
                int traLai = cursor.getInt(5);
                int tongTien = cursor.getInt(6);
                String trangThai = cursor.getString(7);
                HoaDon hoaDon = new HoaDon(maHD, tenKh, chietKhau, khachTra, traLai, tongTien, simpleDateFormat.parse(ngayBan), trangThai);
                list.add(hoaDon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public HoaDon getHoaDonTheoMa(String ma) throws ParseException {
        String query = "Select * from " + TABLE_NAME + " where maHoaDon = '" + ma + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        HoaDon hoaDon = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int maHD = Integer.parseInt(cursor.getString(0));
            String ngayBan = cursor.getString(1);
            String tenKh = cursor.getString(2);
            int chietKhau = cursor.getInt(3);
            int khachTra = cursor.getInt(4);
            int traLai = cursor.getInt(5);
            int tongTien = cursor.getInt(6);
            String trangThai = cursor.getString(7);
            hoaDon = new HoaDon(maHD, tenKh, chietKhau, khachTra, traLai, tongTien, simpleDateFormat.parse(ngayBan), trangThai);
            cursor.close();
        }
        return hoaDon;
    }

    public double  getDoanhThu(String time) {
        String query;
        if (time.equalsIgnoreCase("T???t C???")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME;
        } else if (time.equalsIgnoreCase("H??m nay")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where ngayBan = date('now')";
        } else if (time.equalsIgnoreCase("H??m qua")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        } else if (time.equalsIgnoreCase("Tu???n n??y")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where strftime('%W',ngayBan) = strftime('%W','now') ";
        } else if (time.equalsIgnoreCase("Tu???n tr?????c")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1)";
        } else if (time.equalsIgnoreCase("Th??ng n??y")) {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where strftime('%m',ngayBan) = strftime('%m','now')";
        } else {
            query = "Select SUM(tongTien) from " + TABLE_NAME + " where  ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') ";
        }

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        double tien = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tien += cursor.getDouble(0);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return tien;
    }

    public int getSoHoaDon(String time) {
        String query;
        if (time.equalsIgnoreCase("T???t C???")) {
            query = "Select maHoaDon from " + TABLE_NAME;
        } else if (time.equalsIgnoreCase("H??m nay")) {
            query = "Select maHoaDon from " + TABLE_NAME + " where ngayBan = date('now')";
        } else if (time.equalsIgnoreCase("H??m qua")) {
            query = "Select maHoaDon from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        } else if (time.equalsIgnoreCase("Tu???n n??y")) {
            query = "Select maHoaDon from " + TABLE_NAME + " where strftime('%W',ngayBan) = strftime('%W','now') ";
        } else if (time.equalsIgnoreCase("Tu???n tr?????c")) {
            query = "Select maHoaDon from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1)";
        } else if (time.equalsIgnoreCase("Th??ng n??y")) {
            query = "Select maHoaDon from " + TABLE_NAME + " where strftime('%m',ngayBan) = strftime('%m','now')";
        } else {
            query = "Select maHoaDon from " + TABLE_NAME + " where ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') ";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int soLuong = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                soLuong++;
                cursor.moveToNext();
            }
        }
        cursor.close();
        return soLuong;
    }

    public int getSoTienVon(String time) {
        //String query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet ON SanPham.maSanPham = HoaDonChiTiet.masanpham";
        String query;
        if (time.equalsIgnoreCase("T???t C???")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet" +
                    " ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon";
        } else if (time.equalsIgnoreCase("H??m nay")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where ngayBan = date('now')";
        } else if (time.equalsIgnoreCase("H??m qua")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where  ngayBan = date('now','-1 day')";
        } else if (time.equalsIgnoreCase("Tu???n n??y")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where strftime('%W',ngayBan) = strftime('%W','now') ";
        } else if (time.equalsIgnoreCase("Tu???n tr?????c")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1);";
        } else if (time.equalsIgnoreCase("Th??ng n??y")) {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where strftime('%m',ngayBan) = strftime('%m','now')";
        } else {
            query = "SELECT SUM(SanPham.gianhap*HoaDonChiTiet.soluong) as tienVon from SanPham INNER JOIN HoaDonChiTiet " +
                    "ON SanPham.maSanPham = HoaDonChiTiet.maSanPham JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon " +
                    "where ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') ";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int tongTienVon = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tongTienVon = cursor.getInt(0);
        }
        cursor.close();
        return tongTienVon;
    }

    public double getDoanhThuTheoThang(String thang) {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongTien)  from HoaDon where strftime('%m',ngayBan)='" + thang + "' ";
        Cursor c = sqLiteDatabase.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            doanhThu += c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public List<HoaDon> getAllHoaDonTime(String time, String trangthaihd) throws ParseException {
        String query = null;
        List<HoaDon> list = new ArrayList<>();
        //T???t c???
        if (time.equalsIgnoreCase("T???t C???") && trangthaihd.equalsIgnoreCase("T???t C???")) {
            query = "Select * from " + TABLE_NAME;
        }
        if (time.equalsIgnoreCase("T???t C???") && trangthaihd.equalsIgnoreCase("Ch??a thanh To??n")) {
            query = "Select * from " + TABLE_NAME + " where  trangThai like 'Ch??a Thanh To??n'";
        }
        if (time.equalsIgnoreCase("T???t C???") && trangthaihd.equalsIgnoreCase("???? thanh To??n")) {
            query = "Select * from " + TABLE_NAME + " where  trangThai like '???? Thanh To??n'";
        }
        //H??m nay
        if (time.equalsIgnoreCase("H??m nay") && trangthaihd.equalsIgnoreCase("T???t c???")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%d',ngayBan) = strftime('%d','now') ";
        }
        if (time.equalsIgnoreCase("H??m nay") && trangthaihd.equalsIgnoreCase("Ch??a thanh To??n")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%d',ngayBan) = strftime('%d','now') and trangThai like 'Ch??a Thanh To??n'";
        }
        if (time.equalsIgnoreCase("H??m nay") && trangthaihd.equalsIgnoreCase("???? thanh To??n")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%d',ngayBan) = strftime('%d','now') and trangThai like '???? Thanh To??n'";
        }

        //h??m qua
        if (time.equalsIgnoreCase("H??m qua") && trangthaihd.equalsIgnoreCase("T???t c???")) {
            query = "Select * from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        }
        if (time.equalsIgnoreCase("H??m qua") && trangthaihd.equalsIgnoreCase("Ch??a thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        }
        if (time.equalsIgnoreCase("H??m qua") && trangthaihd.equalsIgnoreCase("???? thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where  ngayBan = date('now','-1 day') ";
        }
        //tu???n n??y
        if (time.equalsIgnoreCase("Tu???n n??y") && trangthaihd.equalsIgnoreCase("T???t c???")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%W',ngayBan)= strftime('%W','now') ";
        }
        if (time.equalsIgnoreCase("Tu???n n??y") && trangthaihd.equalsIgnoreCase("Ch??a thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%W',ngayBan)  and trangThai like 'Ch??a Thanh To??n' ";
        }
        if (time.equalsIgnoreCase("Tu???n n??y") && trangthaihd.equalsIgnoreCase("???? thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%W',ngayBan) and trangThai like '???? Thanh To??n' ";
        }
        //tu???n tr?????c
        if (time.equalsIgnoreCase("Tu???n tr?????c") && trangthaihd.equalsIgnoreCase("T???t c???")) {
            query = "Select * from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1);";
        }
        if (time.equalsIgnoreCase("Tu???n tr?????c") && trangthaihd.equalsIgnoreCase("Ch??a thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1) and trangThai like 'Ch??a Thanh To??n' ";
        }
        if (time.equalsIgnoreCase("Tu???n tr?????c") && trangthaihd.equalsIgnoreCase("???? thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1) and trangThai like '???? Thanh To??n' ";
        }
        //th??ng n??y
        if (time.equalsIgnoreCase("Th??ng n??y") && trangthaihd.equalsIgnoreCase("T???t c???")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%m',ngayBan) = strftime('%m','now')";
        }
        if (time.equalsIgnoreCase("Th??ng n??y") && trangthaihd.equalsIgnoreCase("Ch??a thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%m',ngayBan) and trangThai like 'Ch??a Thanh To??n' ";
        }
        if (time.equalsIgnoreCase("Th??ng n??y") && trangthaihd.equalsIgnoreCase("???? thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%m',ngayBan) and trangThai like '???? Thanh To??n' ";
        }
        //th??ng tr?????c
        if (time.equalsIgnoreCase("Th??ng tr?????c") && trangthaihd.equalsIgnoreCase("T???t c???")) {
            query = "Select * from " + TABLE_NAME + " where  ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') ";
        }
        if (time.equalsIgnoreCase("Th??ng tr?????c") && trangthaihd.equalsIgnoreCase("Ch??a thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where  ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') and trangThai like 'Ch??a Thanh To??n' ";
        }
        if (time.equalsIgnoreCase("Th??ng tr?????c") && trangthaihd.equalsIgnoreCase("???? thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where ngayban >= date('now','start of month','-1 month') AND ngayban < date('now','start of month') and trangThai like '???? Thanh To??n' ";
        }
        //T???t c??? time
        if (time.equalsIgnoreCase("T???t c???") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME;
        }
        // h??m nay
        if (time.equalsIgnoreCase("H??m Nay") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%d',ngayBan) = strftime('%d','now') ";
        }
        //h??m qua
        if (time.equalsIgnoreCase("h??m qua") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where  ngayBan = date('now','-1 day')";
        }
        //tu???n n??y
        if (time.equalsIgnoreCase("tu???n n??y") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%W',ngayBan)= strftime('%W','now') ";
        }
        //tu???n tr?????c
        if (time.equalsIgnoreCase("tu???n tr?????c") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " WHERE (strftime('%W',ngayban)+0) = (SELECT strftime('%W','now')-1) ";
        }
        //th??ng n??y
        if (time.equalsIgnoreCase("th??ng n??y") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where strftime('%m',ngayBan) = strftime('%m','now')";
        }
        if (time.equalsIgnoreCase("Th??ng tr?????c") && trangthaihd.equalsIgnoreCase("")) {
            query = "Select * from " + TABLE_NAME + " where ngayban >= date('now','start of month','-1 month')" +
                    "AND ngayban < date('now','start of month')";
        }
        //trang thai hoa don
        if (time.equalsIgnoreCase("") && trangthaihd.equalsIgnoreCase("T???t c???")) {
            query = "Select * from " + TABLE_NAME;
        }
        if (time.equalsIgnoreCase("") && trangthaihd.equalsIgnoreCase("Ch??a thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where trangThai like 'Ch??a thanh to??n' ";
        }
        if (time.equalsIgnoreCase("") && trangthaihd.equalsIgnoreCase("???? thanh to??n")) {
            query = "Select * from " + TABLE_NAME + " where trangThai like '???? thanh to??n'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int maHD = Integer.parseInt(cursor.getString(0));
                String ngayBan = cursor.getString(1);
                String tenKh = cursor.getString(2);
                int chietKhau = cursor.getInt(3);
                int khachTra = cursor.getInt(4);
                int traLai = cursor.getInt(5);
                int tongTien = cursor.getInt(6);
                String trangThai = cursor.getString(7);
                HoaDon hoaDon = new HoaDon(maHD, tenKh, chietKhau, khachTra, traLai, tongTien, simpleDateFormat.parse(ngayBan), trangThai);
                list.add(hoaDon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }


}
