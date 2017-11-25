class CreateTechnicianSkills < ActiveRecord::Migration[5.1]
  def change
    create_table :technician_skills do |t|
      t.string    "name"
      t.string    "adquired_date"
      t.integer   "has_certification"
      t.string	  "certification_description"
      t.timestamps
    end
  end
end
