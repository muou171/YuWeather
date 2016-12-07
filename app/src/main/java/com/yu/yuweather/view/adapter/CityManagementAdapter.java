package com.yu.yuweather.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.ColorUtils;
import com.yu.yuweather.utils.IconUtils;

import java.util.List;

public class CityManagementAdapter extends RecyclerView.Adapter<CityManagementAdapter.CityManagementViewHolder> {

    private List<Now.BasicBean> basicBeanList;
    private YuWeatherDB yuWeatherDB;
    private Activity activity;

    private OnItemClickListener onItemClickListener;

    public void updateBasicBeanList(List<Now.BasicBean> basicBeanList) {
        this.basicBeanList = basicBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CityManagementAdapter(List<Now.BasicBean> basicBeanList, YuWeatherDB yuWeatherDB, Activity activity) {
        this.basicBeanList = basicBeanList;
        this.yuWeatherDB = yuWeatherDB;
        this.activity = activity;
    }

    @Override
    public CityManagementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CityManagementViewHolder cityManagementViewHolder = new CityManagementViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_detail_now, parent, false));
        return cityManagementViewHolder;
    }

    @Override
    public void onBindViewHolder(CityManagementViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return basicBeanList == null ? 0 : basicBeanList.size();
    }

    class CityManagementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvItemBasicCity;
        private TextView tvItemNowTmp;
        private TextView tvItemNowTmpUnit;
        private TextView tvItemNowWindDir;
        private ImageView ivWindDirection;
        private TextView tvItemBasicUpdateLoc;
        private TextView tvItemNowCondTxt;
        private ImageView ivItemNowCondCode;
        private LinearLayout llItemContentDetailNow;

        public CityManagementViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvItemBasicCity = (TextView) itemView.findViewById(R.id.tv_item_basic_city);
            tvItemNowTmp = (TextView) itemView.findViewById(R.id.tv_item_now_tmp);
            tvItemNowTmpUnit = (TextView) itemView.findViewById(R.id.tv_item_now_tmp_unit);
            tvItemNowWindDir = (TextView) itemView.findViewById(R.id.tv_item_now_wind_dir);
            ivWindDirection = (ImageView) itemView.findViewById(R.id.iv_wind_direction);
            tvItemBasicUpdateLoc = (TextView) itemView.findViewById(R.id.tv_item_basic_update_loc);
            tvItemNowCondTxt = (TextView) itemView.findViewById(R.id.tv_item_now_cond_txt);
            ivItemNowCondCode = (ImageView) itemView.findViewById(R.id.iv_item_now_cond_code);
            llItemContentDetailNow = (LinearLayout) itemView.findViewById(R.id.ll_item_content_detail_now);
        }

        private void setData(int position) {
            Now.BasicBean basicBean = basicBeanList.get(position);
            Now.NowBean nowBean = yuWeatherDB.loadNow(basicBean.getId());
            String code = nowBean.getCond().getCode();
            if (code.equals("400") || code.equals("401") || code.equals("402") || code.equals("403") || code.equals("407")) {
                tvItemBasicCity.setTextColor(ColorUtils.GetResourceColor(activity, R.color.colorGrayLight));
                tvItemNowTmp.setTextColor(ColorUtils.GetResourceColor(activity, R.color.colorGrayDark));
                tvItemNowTmpUnit.setTextColor(ColorUtils.GetResourceColor(activity, R.color.colorGrayDark));
                tvItemNowWindDir.setTextColor(ColorUtils.GetResourceColor(activity, R.color.colorGrayLight));
                ivWindDirection.setImageResource(R.drawable.ic_wind_direction_gray);
                tvItemBasicUpdateLoc.setTextColor(ColorUtils.GetResourceColor(activity, R.color.colorGrayLight));
                tvItemNowCondTxt.setTextColor(ColorUtils.GetResourceColor(activity, R.color.colorGrayDark));
            }
            llItemContentDetailNow.setBackgroundResource(ColorUtils.GetNowBackgroundColor(code));
            ivItemNowCondCode.setImageResource(IconUtils.GetCondIconResourcesId(code));
            tvItemBasicUpdateLoc.setText(basicBean.getUpdate().getLoc());
            tvItemNowCondTxt.setText(nowBean.getCond().getTxt());
            tvItemNowTmp.setText(nowBean.getTmp());
            tvItemBasicCity.setText(basicBean.getCity());
            tvItemNowWindDir.setText(nowBean.getWind().getDir());
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null && view == itemView) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
