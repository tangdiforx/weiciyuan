package org.qii.weiciyuan.ui.timeline;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import org.qii.weiciyuan.R;

/**
 * Created with IntelliJ IDEA.
 * User: qii
 * Date: 12-7-29
 * Time: 下午12:03
 * To change this template use File | Settings | File Templates.
 */
public class FriendsTimeLineFragment extends AbstractTimeLineFragment {

    private Commander commander;

    public static abstract class Commander {

        public volatile boolean isBusying = false;

        public void getNewFriendsTimeLineMsgList() {
        }

        public void getOlderFriendsTimeLineMsgList() {
        }

        public void replayTo(int position, View view) {
        }

        public void newWeibo() {
        }

        public void onItemClick(int position) {
        }

        public void listViewFooterViewClick(View view) {

        }

        public void downloadAvatar(ImageView view, String url, int position, ListView listView) {

        }

        public void downContentPic(ImageView view, String url, int position, ListView listView) {

        }
    }

    public FriendsTimeLineFragment setCommander(Commander commander) {
        this.commander = commander;
        return this;
    }

    @Override
    protected void scrollToBottom() {
        commander.getOlderFriendsTimeLineMsgList();
    }

    @Override
    public void listViewItemLongClick(AdapterView parent, View view, int position, long id) {
        view.setSelected(true);
        new MyAlertDialogFragment().setView(view).setPosition(position).show(getFragmentManager(), "");
    }

    @Override
    protected void listViewItemClick(AdapterView parent, View view, int position, long id) {
        commander.onItemClick(position);
    }

    @Override
    protected void rememberListViewPosition(int position) {
        activity.setHomelist_position(position);
    }

    @Override
    protected void listViewFooterViewClick(View view) {
        commander.listViewFooterViewClick(view);
    }

    @Override
    protected void downloadAvatar(ImageView view, String url, int position, ListView listView) {
        commander.downloadAvatar(view, url, position, listView);
    }

    @Override
    protected void downContentPic(ImageView view, String url, int position, ListView listView) {
        commander.downContentPic(view, url, position, listView);
    }


    public void refresh() {
        timeLineAdapter.notifyDataSetChanged();
        listView.setSelectionAfterHeaderView();
    }

    @Override
    public void refreshAndScrollTo(int position) {
        refresh();
        listView.smoothScrollToPosition(position);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.friendstimelinefragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.friendstimelinefragment_new_weibo:
                commander.newWeibo();
                break;
            case R.id.friendstimelinefragment_refresh:
                commander.getNewFriendsTimeLineMsgList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    class MyAlertDialogFragment extends DialogFragment {
        View view;
        int position;

        @Override
        public void onCancel(DialogInterface dialog) {
            view.setSelected(false);
        }

        public MyAlertDialogFragment setView(View view) {
            this.view = view;
            return this;
        }

        public MyAlertDialogFragment setPosition(int position) {
            this.position = position;
            return this;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            String[] items = {"刷新", "回复"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.select))
                    .setItems(items, onClickListener);

            return builder.create();
        }

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        commander.getNewFriendsTimeLineMsgList();
                        break;
                    case 1:
                        commander.replayTo(position, view);
                        break;
                }
            }
        };
    }


}

