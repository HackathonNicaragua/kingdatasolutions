class CreateTechnicians < ActiveRecord::Migration[5.1]
  def change
    create_table :technicians do |t|
      t.string    "full_name",		limit: 100
      t.integer   "gender"
      t.string    "phone",		limit: 100
      t.integer    "municipality"
      t.string    "description",	limit: 140      
      t.string	  "image_link"
      t.timestamps
    end
  end
end
