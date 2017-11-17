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
import com.out.accu.link.data.util.ByteUtil;
import com.out.accu.link.router.Navigation;

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
            mTvAliasName.setText("设备:" + ByteUtil.getId(device.id));
            if(device.isOnline) {
                mTvStatus.setText("在线");
            } else {
                mTvStatus.setText("离线");
            }
        }
    }
}
