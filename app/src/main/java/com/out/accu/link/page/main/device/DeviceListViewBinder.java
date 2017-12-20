package com.out.accu.link.page.main.device;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyou17173.android.arch.base.adapter.SmartViewHolder;
import com.out.accu.link.R;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.router.Navigation;
import com.out.accu.link.util.DeviceUtil;
import com.out.accu.link.util.NumberUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/13
 */

public class DeviceListViewBinder extends ItemViewBinder<Device, DeviceListViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Device item) {
        holder.setData(item);
    }

    class ViewHolder extends SmartViewHolder<Device> {
        @BindView(R.id.aliasName)
        TextView mTvAliasName;
        @BindView(R.id.status)
        TextView mTvStatus;
        @BindView(R.id.channel1Value)
        TextView mTvChannel1Value;
        @BindView(R.id.channel2Value)
        TextView mTvChannel2Value;
        @BindView(R.id.sampleValue)
        TextView mTvSampleValue;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                Navigation.deviceDetail((Activity) getContext(), getItemData().id);
            });
        }

        @Override
        public void setData(Device device) {
            super.setData(device);
            mTvAliasName.setText(DeviceUtil.getDeviceName(device));
            if(device.isOnline) {
                mTvStatus.setText(R.string.online);
                mTvStatus.setTextColor(getContext().getResources().getColor(R.color.esv_google_green));
            } else {
                mTvStatus.setText(R.string.offline);
                mTvStatus.setTextColor(getContext().getResources().getColor(android.R.color.darker_gray));
            }

            if(device.channel1Value != -1) {
                String value = NumberUtil.formateInt(device.channel1Value);
                mTvChannel1Value.setText(getContext().getResources().getString(R.string.channel1_value, value));
                mTvChannel1Value.setVisibility(View.VISIBLE);
            } else {
                mTvChannel1Value.setText("");
                mTvChannel1Value.setVisibility(View.GONE);
            }

            if(device.channel2Value != -1) {
                String value = NumberUtil.formateInt(device.channel2Value);
                mTvChannel2Value.setText(getContext().getResources().getString(R.string.channel2_value, value));
                mTvChannel2Value.setVisibility(View.VISIBLE);
            } else {
                mTvChannel2Value.setText("");
                mTvChannel2Value.setVisibility(View.GONE);
            }

            if(device.sampleValue != -1) {
                String value = getContext().getResources().getString(R.string.sample_value, device.sampleValue/1000);
                if(device.samplePercent != null) {
                    value += " [" + device.samplePercent + "%]";
                }
                mTvSampleValue.setText(value);
                mTvSampleValue.setVisibility(View.VISIBLE);
            } else {
                mTvSampleValue.setText("");
                mTvSampleValue.setVisibility(View.GONE);
            }
        }
    }
}
