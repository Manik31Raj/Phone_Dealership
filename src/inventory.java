import java.sql.*;
import java.util.*;

class Inventory {

    public void addPhone(Phone phone) {
        try (Connection conn = DBUtil.getConnection()) {
            // check if phone exists
            String query = "SELECT id, quantity FROM inventory WHERE brand=? AND model=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, phone.getBrand());
            ps.setString(2, phone.getModel());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int existingQty = rs.getInt("quantity");
                String update = "UPDATE inventory SET quantity=? WHERE id=?";
                PreparedStatement updateStmt = conn.prepareStatement(update);
                updateStmt.setInt(1, existingQty + phone.getQuantity());
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();
            } else {
                String insert = "INSERT INTO inventory (brand, model, price, quantity) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insert);
                insertStmt.setString(1, phone.getBrand());
                insertStmt.setString(2, phone.getModel());
                insertStmt.setDouble(3, phone.getPrice());
                insertStmt.setInt(4, phone.getQuantity());
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Phone> getAllPhones() {
        List<Phone> phones = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM inventory";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                phones.add(new Phone(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phones;
    }

    public void sellPhone(int id, int quantity) {
        try (Connection conn = DBUtil.getConnection()) {
            String check = "SELECT quantity FROM inventory WHERE id=?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQty = rs.getInt("quantity");
                if (quantity <= currentQty) {
                    int remaining = currentQty - quantity;
                    if (remaining == 0) {
                        String delete = "DELETE FROM inventory WHERE id=?";
                        PreparedStatement deleteStmt = conn.prepareStatement(delete);
                        deleteStmt.setInt(1, id);
                        deleteStmt.executeUpdate();
                    } else {
                        String update = "UPDATE inventory SET quantity=? WHERE id=?";
                        PreparedStatement updateStmt = conn.prepareStatement(update);
                        updateStmt.setInt(1, remaining);
                        updateStmt.setInt(2, id);
                        updateStmt.executeUpdate();
                    }
                    System.out.println("Sold " + quantity + " unit(s).");
                } else {
                    System.out.println("Not enough stock. Only " + currentQty + " left.");
                }
            } else {
                System.out.println("Phone not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
