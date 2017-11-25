class CreateTechnicianSkills < ActiveRecord::Migration[5.1]
  def change
    create_table :technician_skills do |t|
      t.string    "name",		limit: 100
      t.string    "adquired_date",
      t.integer   "has_certification"
      t.string	  "certification_description"	limit: 100
      t.timestamps
    end
  end
end
