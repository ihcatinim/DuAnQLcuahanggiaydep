package com.fpoly.duan1demo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.fpoly.duan1demo.R;
import com.fpoly.duan1demo.database.LoaiSanPhamDAO;
import com.fpoly.duan1demo.object.LoaiSanPham;

import java.util.List;

public class LoaiSanPhamAdapter extends BaseAdapter {
    final Context context;
    final List<LoaiSanPham> list;
    EditText edMa, edTen;

    public LoaiSanPhamAdapter(Context context, List<LoaiSanPham> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_loai_san_pham, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imgDelete = view.findViewById(R.id.imgDeleteLSP);
            viewHolder.imgUpdate = view.findViewById(R.id.imgUpdateLSP);
            viewHolder.tvTen = view.findViewById(R.id.tvTenLSP);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        final LoaiSanPham loaiSanPham = list.get(i);
        viewHolder.tvTen.setText("" + loaiSanPham.getTenLoai());
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("X??c nh???n");
                b.setMessage("B???n c?? ?????ng ?? x??a lo???i h??ng n??y kh??ng?");
                b.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        long chk = loaiSanPhamDAO.deleteLoaiSanPham(list.get(i).getMaLoai());
                        if (chk > 0) {
                            Toast.makeText(context, "X??a th??nh c??ng", Toast.LENGTH_SHORT).show();
                            list.remove(i);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "X??a kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                b.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                b.show();
            }
        });
        viewHolder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.activity_sua_loai_san_pham);
                dialog.show();
                edMa = dialog.findViewById(R.id.edSuaMaMatHang);
                edMa.setEnabled(false);
                edTen = dialog.findViewById(R.id.edSuaTenMatHang);
                edMa.setText("" + list.get(i).getMaLoai());
                edTen.setText("" + list.get(i).getTenLoai());
                ImageView imgLuu = dialog.findViewById(R.id.imgLuuSuaLSP);
                imgLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ten = edTen.getText().toString();
                        if (ten.equalsIgnoreCase("")) {
                            Toast.makeText(context, "D??? li???u kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        LoaiSanPham loaiSanPham1 = new LoaiSanPham(0, ten);
                        try {
                            loaiSanPhamDAO.updateLoaiSanPham(loaiSanPham1);
                            Toast.makeText(context, "L??u th??nh c??ng", Toast.LENGTH_SHORT).show();
                            list.set(i, loaiSanPham1);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(context, "L??u kh??ng th??nh c??ng, m?? lo???i ???? t???n t???i", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

    private static class ViewHolder {
        TextView tvTen;
        ImageView imgDelete, imgUpdate;
    }
}
