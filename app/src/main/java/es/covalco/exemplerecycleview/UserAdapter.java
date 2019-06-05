package es.covalco.exemplerecycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

  private List<UserModel> userModelList;
  public UserAdapter(List<UserModel> userModelList) {
    this.userModelList = userModelList;
  }

  // Creamos la vista sin personalizar
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.user_list_row, parent, false);
    ViewHolder viewHolder = new ViewHolder(v);
    return viewHolder;
  }

  // Personalizamos el ViewHolder
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    String name = userModelList.get(position).getName();
    holder.name.setText(name);
  }

  // Indicamos el número de elementos de la lista
  public int getItemCount() {
    return userModelList.size();
  }

  // Definim el ViewHolder que utilitzarà el Adapter
  public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    public ViewHolder(View v) {
      super(v);
      name = (TextView) v.findViewById(R.id.textUserName);
    }
  }
}
