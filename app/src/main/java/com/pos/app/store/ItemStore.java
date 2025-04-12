package com.pos.app.store;

import com.pos.app.model.Inventory;
import com.pos.app.model.Item;
import com.pos.app.model.Location;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Data;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class ItemStore {
    // Danh sách các item theo vị trí
    public static final MapProperty<String, List<Item>> itemsPerLocation = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));

    
    // Danh sách các item hiển thị trên bảng
    public static final ListProperty<Item> visibleItems = new SimpleListProperty<>(FXCollections.synchronizedObservableList(FXCollections.observableList(new ArrayList<>())));

    // Danh sách các vị trí
    public static final ListProperty<Location> locations = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    // Vị trí hiện tại
    public static Location currentLocation;

    // Danh sách inventory
    public static final MapProperty<Integer, List<Inventory>> inventories = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));   // Lưu inventory cho mỗi item

    // Số lượng item trên mỗi trang
    public static final IntegerProperty pageSize = new SimpleIntegerProperty(20);

    // Số lượng trang
    public static final IntegerProperty pageCount = new SimpleIntegerProperty(0);

    public static final IntegerProperty currentPage = new SimpleIntegerProperty();




    // ---------------------- Khởi tạo dữ liệu mẫu ----------------------//
    static {

        // Thêm dữ liệu vào danh sách tên vị trí
        locations.get().addAll( new ArrayList<>( List.of( new Location(1,"Stock 1",false),
                new Location(2,"Stock 2",false),
                new Location(3,"Stock 3",false),
                new Location(4,"Stock 4",false)
        )));

        // Vị trí hiện tại
        currentLocation = new Location(0,"Stock",false);

        // Nếu giá trị của vị trí hiện tại thay đổi thì cập nhật danh sách item tương ứng để hiển thị trên bảng
        currentLocation.getName().addListener((observable, oldValue, newValue) -> {
            visibleItems.clear();
            itemsPerLocation.computeIfPresent(newValue, (key, value) -> {
                pageCount.set((int) Math.ceil((double) value.size() / ItemStore.pageSize.getValue()));
                int fromIndex = currentPage.get() * ItemStore.pageSize.getValue();
                System.out.println("Current page: " + currentPage.get());
                int toIndex = Math.min(fromIndex + ItemStore.pageSize.getValue(), value.size());
                visibleItems.addAll(itemsPerLocation.get(newValue).subList(fromIndex, toIndex));
                System.out.println("fromIndex: " + fromIndex + " toIndex: " + toIndex);
                return value;
            });
        });

        // Khi chuyển trang thì cập nhật lại danh sách item hiển thị
        currentPage.addListener((observable, oldValue, newValue) -> {
            visibleItems.clear();
            itemsPerLocation.computeIfPresent(currentLocation.getName().getValue(), (key, value) -> {
                int fromIndex = currentPage.get() * ItemStore.pageSize.getValue();
                System.out.println("Current page: " + currentPage.get());
                int toIndex = Math.min(fromIndex + ItemStore.pageSize.getValue(), value.size());
                System.out.println("fromIndex: " + fromIndex + " toIndex: " + toIndex);
                visibleItems.addAll(value.subList(fromIndex, toIndex));
                return value;
            });
        });

        // Chọn vị trí hiện tại là vị trí đầu tiên
        currentLocation.getName().set(locations.getFirst().getName().get());
    }
    
}
