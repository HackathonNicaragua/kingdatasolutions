class CreateJobSkills < ActiveRecord::Migration[5.1]
  def change
    create_table :job_skills do |t|
      t.integer		"priority"      
      t.timestamps
    end
  end
end
