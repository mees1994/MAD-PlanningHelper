//package ass.mad.arnhem.han.planninghelper;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
///**
// * Created by Mees on 5/17/2016.
// */
//public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.myViewHolder> {
//
//    private static final int MOVIE_DETAIL = 0;
//    private static final int MOVIE_VIDEO = 1;
//    private static final int MOVIE_REVIEW = 2;
//    private static final int MOVIE_DETAIL_SECTION_HEADER = 3;
//    private final static String BASE_URL = "http://image.tmdb.org/t/p/w185/";
//
//    private LayoutInflater inflater;
//    private Context context;
//    private List<RecyclerviewMovieDetail> items = new ArrayList<>();
//
//    public MovieDetailAdapter(Context context) {
//        inflater = LayoutInflater.from(context);
//        this.context = context;
//    }
//
//    public void addItem(RecyclerviewMovieDetail recyclerviewMovieDetail) {
//        items.add(recyclerviewMovieDetail);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        myViewHolder holder;
//        if (viewType == MOVIE_DETAIL) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_detail, parent, false);
//            holder = new myViewHolder(view);
//        } else if (viewType == MOVIE_VIDEO) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_video, parent, false);
//            holder = new myViewHolder(view);
//        } else if (viewType == MOVIE_REVIEW) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_review, parent, false);
//            holder = new myViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_detail_section_title, parent, false);
//            holder = new myViewHolder(view);
//        }
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(final myViewHolder holder, int position) {
//        final RecyclerviewMovieDetail current = items.get(position);
//        if (getItemViewType(position) == MOVIE_DETAIL) {
//            holder.movieTitle.setText(current.getMovieTitle());
//            Picasso.with(context)
//                    .load(BASE_URL + current.getDetailImage())
//                    .resize(context.getResources().getDisplayMetrics().widthPixels / 3, context.getResources().getDisplayMetrics().heightPixels /3)
//                    .into(holder.detailImage);
//            holder.movieReleaseDate.setText(current.getMovieReleaseDate());
//            holder.movieScore.setText(current.getMovieScore());
//            holder.movieOverview.setText(current.getMovieOverview());
//            String btnText = current.isFavorite() ? "Unmark as favorite" : "Mark as favorite";
//            holder.favoriteBtn.setText(btnText);
//            holder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (current.isFavorite()) {
//                        holder.favoriteBtn.setText("Mark as favorite");
//                        current.setIsFavorite(false);
//
//                        int deleted = 0;
//                        deleted = context.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,  "_id=?", new String[]{current.getMovieId() +""});
//                        Log.d(this.toString(), "Favorite deleted. " + deleted + " Deleted");
//                    } else {
//                        holder.favoriteBtn.setText("Unmark as favorite");
//                        current.setIsFavorite(true);
//                        Vector<ContentValues> cVVector = new Vector<>();
//
//                        ContentValues movieValues = new ContentValues();
//
//                        movieValues.put(MovieContract.MovieEntry._ID, current.getMovieId());
//                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, current.getMovieTitle());
//                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, current.getDetailImage());
//                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, current.getMovieReleaseDate());
//                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_SCORE, current.getMovieScore());
//                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, current.getMovieOverview());
//
//                        cVVector.add(movieValues);
//
//                        int inserted = 0;
//                        // add to database
//                        if ( cVVector.size() > 0 ) {
//                            ContentValues[] cvArray = new ContentValues[cVVector.size()];
//                            cVVector.toArray(cvArray);
//                            inserted = context.getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI, cvArray);
//                        }
//                        Log.d(this.toString(), "Favorite Saved. " + inserted + " Inserted");
//                    }
//                }
//            });
//        } else if (getItemViewType(position) == MOVIE_VIDEO) {
//            holder.videoName.setText(current.getVideoName());
//            holder.layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + current.getVideoKey())));
//                }
//            });
//        } else if (getItemViewType(position) == MOVIE_REVIEW) {
//            holder.reviewAuthor.setText(current.getReviewAuthor());
//            holder.reviewContent.setText(current.getReviewContent());
//        } else if (getItemViewType(position) == MOVIE_DETAIL_SECTION_HEADER) {
//            holder.sectionHeader.setText(current.getSectionHeader());
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        RecyclerviewMovieDetail item;
//        item = getDataByPosition(position);
//        return item.getViewType();
//    }
//
//    public RecyclerviewMovieDetail getDataByPosition(int position) {
//        return items.get(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public class myViewHolder extends RecyclerView.ViewHolder {
//        TextView taskTitle;
//        TextView taskDescription;
//        TextView startTime;
//        TextView endTime;
//
//        ImageView icon;
//
//        public myViewHolder(View itemView) {
//            super(itemView);
//            taskTitle = (TextView) itemView.findViewById(R.id.movie_title);
//
//        }
//    }
//}
