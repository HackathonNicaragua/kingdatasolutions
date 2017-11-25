class CreateTechnicianTypes < ActiveRecord::Migration[5.1]
  def change
    create_table :technician_types do |t|
      t.string    "name",		limit: 50      
      t.timestamps
    end
  end
end
