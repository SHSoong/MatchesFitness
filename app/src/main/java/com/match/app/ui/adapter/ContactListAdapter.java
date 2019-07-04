package com.match.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.match.app.message.entity.Contact;
import com.match.app.message.entity.SortModel;
import com.matches.fitness.R;

import java.util.List;

public class ContactListAdapter extends BaseAdapter implements SectionIndexer{
    private Context mContext;
    private List<SortModel> mLists;

    public ContactListAdapter(Context context, List<SortModel> lists) {
        this.mContext = context;
        this.mLists = lists;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<SortModel> list){
        this.mLists = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.itemview_contact, null);
            holder.imgHeader = view.findViewById(R.id.img_header);
            holder.tvWord = view.findViewById(R.id.tv_word);
            holder.tvName = view.findViewById(R.id.tv_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        SortModel contact = mLists.get(i);
        String word = contact.getName();
        holder.tvWord.setText(word);
        holder.tvName.setText(contact.getName());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_avatar);
        Glide.with(mContext)
                .load(contact.getUrl())
                .apply(options)
                .into(holder.imgHeader);
        if (i == 0) {
            holder.tvWord.setVisibility(View.VISIBLE);
        } else {
            String headerWord = mLists.get(i - 1).getSortLetters();
            if (contact.getSortLetters().equals(headerWord)) {
                holder.tvWord.setVisibility(View.GONE);
            } else {
                holder.tvWord.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    private class ViewHolder {
        private TextView tvWord;
        private ImageView imgHeader;
        private TextView tvName;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mLists.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mLists.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
