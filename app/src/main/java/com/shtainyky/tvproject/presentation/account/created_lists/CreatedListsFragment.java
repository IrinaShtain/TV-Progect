package com.shtainyky.tvproject.presentation.account.created_lists;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.adapter.CreatedListsAdapter;
import com.shtainyky.tvproject.presentation.account.created_lists.adapter.CreatedListsDH;
import com.shtainyky.tvproject.presentation.account.created_lists.create_list.CreateNewListDialog;

import com.shtainyky.tvproject.presentation.account.created_lists.create_list.CreateNewListDialog_;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.MoviesInListFragment_;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshableFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
import com.shtainyky.tvproject.presentation.listeners.EndlessScrollListener;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Bell on 25.05.2017.
 */
@EFragment
public class CreatedListsFragment extends RefreshableFragment implements CreatedListsContract.CreatedListsView, OnCardClickListener {

    @ViewById
    RecyclerView rvLists;

    @Bean
    protected SignedUserManager userManager;
    @Bean
    protected AccountRepository repository;
    private CreatedListsContract.CreatedListsPresenter presenter;
    private CreateNewListDialog newListDialog;

    @Bean
    protected CreatedListsAdapter listAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected RefreshablePresenter getPresenter() {
        return presenter;
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new CreateListsPresenter(this, userManager, repository);
    }

    @Override
    public void setPresenter(CreatedListsContract.CreatedListsPresenter presenter) {
        this.presenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        mActivity.getToolbarManager().setTitle(R.string.title_my_lists);
        setupRecyclerView();
        setupSwipeToRemove();
        setupFab();

        presenter.subscribe();
    }

    private void setupFab(){
        fabAdd_VC.setVisibility(View.VISIBLE);
        fabAdd_VC.setOnClickListener(v -> {
            Log.e("myLog", "onClick FAB ");
            newListDialog = CreateNewListDialog_.builder()
                    .build();
            newListDialog.setTargetFragment(this, Constants.REQUEST_CODE_CREATE_NEW_LIST);
            newListDialog.show(mActivity.getSupportFragmentManager(), "create_list");
        });

    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLists.setLayoutManager(layoutManager);
        listAdapter.setListener(this);
        rvLists.setAdapter(listAdapter);
        rvLists.addOnScrollListener(new EndlessScrollListener(layoutManager, () -> {
            presenter.getNextPage();
            Log.e("myLog", "initUI getNextPage ");
            return true;
        }));
    }

    private void setupSwipeToRemove() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                presenter.removeList(listAdapter.getItem(pos), pos);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon = getVectorBitmap(R.drawable.ic_delete_white);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    Paint p = new Paint();

                    if (dX < 0) {
                        p.setColor(Color.parseColor("#f44336"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        c.clipRect(background);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvLists);
    }

    private Bitmap getVectorBitmap(@DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mActivity, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void setLists(ArrayList<CreatedListsDH> createdListsDHs) {
        listAdapter.setListDH(createdListsDHs);
    }

    @Override
    public void addLists(ArrayList<CreatedListsDH> createdListsDHs) {
        listAdapter.addListDH(createdListsDHs);
    }

    @Override
    public void deleteItem(int pos) {
        listAdapter.deleteItem(pos);
    }

    @Override
    public void addItem(CreatedListsDH createdListsDH) {
        listAdapter.addItem(createdListsDH);
    }

    @Override
    public void onCardClick(int itemID, int position) {
        presenter.showDetails(itemID, listAdapter.getItem(position));
    }

    @Override
    public void openListDetails(int lisID, String listsName) {
        mActivity.replaceFragment(MoviesInListFragment_.builder()
                .listID(lisID)
                .listTitle(listsName)
                .build());
    }

    @Override
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        super.showPlaceholder(placeholderType);
        if (placeholderType == Constants.PlaceholderType.EMPTY) {
            ivPlaceholderImage_VC.setImageResource(R.drawable.placeholder_empty_lists);
            tvPlaceholderMessage_VC.setText(R.string.err_msg_no_lists);
        }
    }

    @OnActivityResult(Constants.REQUEST_CODE_CREATE_NEW_LIST)
    public void onDialogFragmentResult(int resultCode, @OnActivityResult.Extra(value = Constants.KEY_TITLE) String title,
                                       @OnActivityResult.Extra(value = Constants.KEY_DESCRIPTION) String description,
                                       @OnActivityResult.Extra(value = Constants.KEY_ERROR_CODE) int errorCode) {
        if (resultCode == Activity.RESULT_OK) {
            presenter.showResult(errorCode, title, description);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (newListDialog != null && newListDialog.isVisible()) newListDialog.dismiss();
    }
}
