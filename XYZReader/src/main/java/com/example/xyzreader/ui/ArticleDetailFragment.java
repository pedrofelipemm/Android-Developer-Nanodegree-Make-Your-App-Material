package com.example.xyzreader.ui;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.utils.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ArticleDetailFragment";

    public static final String ARG_ITEM_ID = "ARG_ITEM_ID";

    @BindView(R.id.photo)
    ImageView mPhotoView;

    @BindView(R.id.article_title)
    TextView mTittleView;

    @BindView(R.id.article_byline)
    TextView mBylineView;

    @BindView(R.id.article_body)
    TextView mBodyView;

    @BindView(R.id.share_fab)
    FloatingActionButton mShareFab;

    private Context mContext;

    private long mItemId;

    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(long itemId) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // In support library r8, calling initLoader for a fragment in a FragmentPagerAdapter in
        // the fragment's onCreate may cause the same LoaderManager to be dealt to multiple
        // fragments because their mIndex is -1 (haven't been added to the activity yet). Thus,
        // we do this in onActivityCreated.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article_detail, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    private Date parsePublishedDate(String date) {
        return DateUtil.parseDefault(date, new Date());
    }

    private void updateUI(Cursor cursor) {

        final String title = cursor.getString(ArticleLoader.Query.TITLE);

        mTittleView.setText(title);
        Date publishedDate = parsePublishedDate(cursor.getString(ArticleLoader.Query.PUBLISHED_DATE));
        if (!publishedDate.before(DateUtil.START_OF_EPOCH.getTime())) {
            // noinspection deprecation
            mBylineView.setText(Html.fromHtml(
                    DateUtils.getRelativeTimeSpanString(
                            publishedDate.getTime(),
                            System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_ALL).toString()
                            + " by <font color='#ffffff'>"
                            + cursor.getString(ArticleLoader.Query.AUTHOR)
                            + "</font>"));

        } else {
            // If date is before 1902, just show the string
            // noinspection deprecation
            mBylineView.setText(Html.fromHtml(
                    DateUtil.formatDefault(publishedDate) + " by <font color='#ffffff'>"
                            + cursor.getString(ArticleLoader.Query.AUTHOR)
                            + "</font>"));

        }

        // noinspection deprecation
        mBodyView.setText(Html.fromHtml(
                cursor.getString(ArticleLoader.Query.BODY).replaceAll("(\r\n|\n)", "<br " + "/>")));

        Picasso.with(mContext)
                .load(cursor.getString(ArticleLoader.Query.PHOTO_URL))
                .into(mPhotoView);

        mShareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(title)
                        .getIntent(), getString(R.string.action_share)));
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (cursor == null || cursor.isClosed() || !cursor.moveToFirst()) {
            Log.e(TAG, "Error reading item detail cursor");
            return;
        }

        updateUI(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        // The loader is not going to be reset.
    }
}
